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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

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
    private final MemberRepository memberRepository;
    private String memberId;

    public String getMemberId() { return memberId; }
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
                .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusHours(6).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                .withClaim("memberId", memberId)
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
                .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusWeeks(2).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                .withClaim("memberId", memberId)
                .sign(Algorithm.HMAC256(memberId));
    }

    /**
     * 액세스토큰의 페이로드를 해석하고 그 정보를 담는 용도로 사용되는 중첩 클래스.
     */
    @Getter
    @NoArgsConstructor
    @ToString
    private static class AccessTokenPayLoad {
        private String iss;
        private long exp;
        private String memberId;
    }

    /**
     * 유저가 전달한 액세스 토큰을 해석하고 충분한 클레임이 있는지 검증 후
     * 모든 페이로드 정보를 Payload 클래스에 담습니다.
     *
     * @param accessToken 유저가 전달한 액세스 토큰
     * @return 액세스 토큰의 페이로드에 존재하는 비공개 클레임 memberId의 값
     */
    public String decodePayload(String accessToken) {
        Base64.Decoder decoder = Base64.getDecoder();
        String base64Payload = accessToken.split("\\.")[1];
        String decoded = new String(decoder.decode(base64Payload), StandardCharsets.UTF_8);
        ObjectMapper convertJsonStringToClass = new ObjectMapper();
        try {
            AccessTokenPayLoad payLoad = convertJsonStringToClass.readValue(decoded, AccessTokenPayLoad.class);
            System.out.println("payLoad: " + payLoad);
            if (payLoad.getMemberId() == null || payLoad.getIss() == null || payLoad.getExp() == 0) {
                log.error("액세스 토큰을 검증하는데 필요한 요소를 페이로드 내에서 찾지 못했습니다. 클라이언트가 변조된 토큰을 전달했을 가능성이 있습니다.");
                throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
            }
            return payLoad.getMemberId();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        throw new InternalAuthenticationServiceException("액세스 토큰의 페이로드를 PayLoad 클래스에 매핑하는 도중 예기치 못한 문제가 발생했습니다.");
    }

    /**
     * 1. 액세스 토큰의 페이로드를 해석하여 비공개 클레임인 memberId를 꺼내와 setTokenIngredient을 수행합니다.
     *
     * if (액세스토큰이 유효하면)
     *   새로운 액세스 토큰 발급 및 반환
     *
     * else if (액세스 토큰이 유효하지 않으면)
     *    리프레시 토큰 검증
     *
     * @param accessToken 유저가 전달한 액세스 토큰
     * @param refreshToken 유저가 전달한 리프레시 토큰
     * @return createAccessToken(), 즉 새로운 액세스 토큰 생성하고 반환
     */
    public String verifyToken(String accessToken, String refreshToken) {
        setTokenIngredient(decodePayload(accessToken));

        if (verifyAccessToken(accessToken)) return createAccessToken();
        else return verifyRefreshToken(refreshToken);
    }

    /**
     * 회원이 서버로 특정 자원을 요청할때 함께 전달한
     * 액세스 토큰과 리프레시 토큰을 순차적으로 검증하는 메서드.
     *
     * @see JWTVerifier
     * JWTVerifier.verify는 토큰이 유효한 형식의 jwt인지를 검증 및
     * 발급자 일치성 및 해당 회원의 고유 id로 액세스 토큰이 생성된 것인지 검증합니다.
     *
     * expClaim과 memberIdClaim이 null인지를 검사 후 수행되는
     * setTokenIngredient(memberIdClaim.asString()) 메서드는
     *
     * verifyAccessToken이 false를 반환할 때 리프레시토큰을 database로부터 가져오기 위한 memberId를
     * 세팅하므로, 반드시 verifyAccessToken 메서드가 verifyRefreshToken 메서드부터 먼저 수행되어야합니다.
     *
     * if (검증 결과 불일치 사항 발생)
     *    유효하지 않은 토큰임을 알리는 예외처리 수행 (아무것도 반환하지 않음)
     *
     * else if (검증이 성공했으나 액세스 토큰의 유효기간이 지남)
     *    return false
     *
     * else if (검증이 성공하고 액세스 토큰 유효기간도 만료되지 않음)
     *    return true
     *
     * @return 토큰 유효기간이 남아있음: true / 토큰 유효기간이 지남: false
     * @since 0.0.1
     */
    public boolean verifyAccessToken(String accessToken) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(memberId))
                    .withClaim("memberId", memberId)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(accessToken);
        } catch (TokenExpiredException expired) {
            return false;
        } catch (JWTVerificationException failedVerification) {
            log.error("액세스 토큰 검증에 실패했습니다. 유효하지 않은 액세스 토큰입니다.");
            log.error("failedVerification: " + failedVerification.getMessage());
            throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
        }
        return true;
    }

    /**
     *
     * 1. 데이터베이스에 저장된 리프레시 토큰과 유저가 전달한 리프레시 토큰이 일치한지
     * 2. 유저가 전달한 리프레시 토큰의 유효기간이 남아있는지
     * 검증합니다.
     *
     * if (검증 결과 불일치 사항 발생)
     *   유효하지 않은 토큰 에러 메시지를 발생
     *
     * else if (검증이 성공했으나 리프레시 토큰의 유효기간이 지남)
     *   유저에게 재로그인을 요청하도록 알리고 특정 url로 리다이렉트하도록 지시해야 함.
     *
     * else if (검증이 성공하고 리프레시 토큰 유효기간도 만료되지 않음)
     *   새로운 액세스 토큰을 생성 후 반환.
     *
     * @return 액세스 토큰
     * @since 0.0.1
     */
    private String verifyRefreshToken(String refreshToken) {
        Member member = memberRepository.findById(Long.parseLong(memberId))
                                    .orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER));
        String savedRefreshToken = member.getRefreshToken();

        if (savedRefreshToken == null) throw new MemberException(MemberPermitErrorCode.TOKEN_EXPIRED);

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(memberId))
                    .withClaim("memberId", memberId)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(refreshToken);

        } catch (TokenExpiredException expired) {
            log.error("리프레시 토큰이 만료되었습니다. 재로그인이 요구됩니다.");
            throw new MemberException(MemberPermitErrorCode.TOKEN_EXPIRED);
        } catch (JWTVerificationException failedVerification) {
            log.error("리프레시 토큰 검증에 실패했습니다. 유효하지 않은 리프레시 토큰입니다.");
            throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
        }

        if (savedRefreshToken.equals(refreshToken)) return createAccessToken();
        else {
            log.error("저장된 리프레시토큰과 유저가 전달한 리프레시토큰이 서로 다릅니다.");
            throw new MemberException(MemberPermitErrorCode.TOKEN_INVALID);
        }
    }
}