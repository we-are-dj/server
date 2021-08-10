package com.dj.server.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token 생성 규칙에 따라 token을 생성하며
 * 추후에 사용자가 가진 token이 유효한지를 검증합니다.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final String ISSUER = "WE_ARE_DJ";
    private final long currentTime = System.currentTimeMillis();
    private final Date ACCESS_EXPIRED_TIME = new Date(currentTime + (1000 * 60 * 120)); // 2 hour
    private final Date REFRESH_EXPIRED_TIME = new Date(currentTime + (1000 * 60 * 60 * 24 * 14)); // 2 weeks

    private final MemberRepository memberRepository;
    private String memberId;

    public void setTokenIngredient(String memberId) {
        this.memberId = memberId;
    }

    /**
     * Member 고유 아이디로 액세스 토큰을 생성합니다.
     *
     * @return 액세스토큰
     * @since 0.0.1
     */
    public String createAccessToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(ACCESS_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(memberId));
    }

    /**
     * Member 고유 아이디로 리프레시 토큰을 생성합니다.
     *
     * @return 리프레시토큰
     * @since 0.0.1
     */
    public String createRefreshToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(REFRESH_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(memberId));
    }

    /**
     * 회원이 서버로 특정 자원을 요청할때 함께 전달한
     * 액세스 토큰과 리프레시 토큰을 순차적으로 검증하는 메서드.
     *
     * 먼저 리프레시토큰을 바탕으로 Database에서 회원의 정보를 가져와
     * 해당 회원의 고유 id를 사용하여 액세스 토큰이 생성된 것인지 검증합니다.
     *
     * if (검증 결과 불일치 사항 발생)
     *    유효하지 않은 토큰 에러 메시지 발생
     *
     * else if (검증이 성공했으나 액세스 토큰의 유효기간이 지남)
     *    verifyRefreshToken 메서드로 넘어가 리프레시 토큰을 검증
     *
     * else if (검증이 성공하고 액세스 토큰 유효기간도 만료되지 않음)
     *    새로운 액세스 토큰을 생성 후 반환.
     *
     * @return 액세스 토큰
     * @since 0.0.1
     */
    public String verifyAccessToken(String accessToken, String refreshToken) {

        Member member = memberRepository.findByRefreshToken(refreshToken)
                             .orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER));
        String memberId = String.valueOf(member.getMemberId());

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(memberId))
                                      .withIssuer(ISSUER)
                                      .build();
            verifier.verify(accessToken);
        } catch (TokenExpiredException expiredToken) {
            log.debug("access token has been expired.", expiredToken);
            return verifyRefreshToken(memberId, member.getRefreshToken(), refreshToken);
        } catch (JWTVerificationException failedVerification) {
            log.error(failedVerification.getMessage());
            throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
        }
        return createAccessToken();
    }

    /**
     * 리프레시 토큰이 유효한지 검증합니다.
     *
     * if (검증 결과 불일치 사항 발생)
     *   유효하지 않은 토큰 에러 메시지를 발생
     *
     * else if (검증이 성공했으나 리프레시 토큰의 유효기간이 지남)
     *   유저에게 재로그인을 요청하도록 알리고 자원 요청전의 url로 리다이렉트하도록 지시해야 함.
     *
     * else if (검증이 성공하고 리프레시 토큰 유효기간도 만료되지 않음)
     *   새로운 액세스 토큰을 생성 후 반환.
     *
     * @return 액세스 토큰
     * @since 0.0.1
     */
    private String verifyRefreshToken(String memberId, String savedRefreshToken, String tobeVerifiedRefreshToken) {

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(memberId))
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(tobeVerifiedRefreshToken);
        } catch (TokenExpiredException expiredToken) {
            log.debug("refresh token has been expired. member has to be signin again.", expiredToken);
            throw new MemberException(MemberPermitErrorCode.TOKEN_EXPIRED);
        } catch (JWTVerificationException failedVerification) {
            log.error(failedVerification.getMessage());
            throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
        }

        if (savedRefreshToken.equals(tobeVerifiedRefreshToken)) return createAccessToken();
        else throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
    }
}