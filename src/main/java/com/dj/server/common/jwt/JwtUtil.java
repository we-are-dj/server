package com.dj.server.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.dj.server.api.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token 생성 규칙에 따라 token을 생성, 또한'
 * 추후에 사용자가 가진 token이 유효한지를 검증합니다.
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */

@Component
public class JwtUtil {
    private final String ISSUER = "WE_ARE_DJ";
    private final Date ACCESS_EXPIRED_TIME = new Date(System.currentTimeMillis() / (1000 * 60 * 10)); // 10 mins
    private final Date REFRESH_EXPIRED_TIME = new Date(System.currentTimeMillis() / (1000 * 60 * 60 * 24 * 2)); // 2 weeks

    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public String createAccessToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(ACCESS_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(String.valueOf(member.getMemberId())));
    }

    public String createRefreshToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(REFRESH_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(String.valueOf(member.getMemberId())));
    }

    public void verifyToken(String givenToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(String.valueOf(member.getMemberId())))
                .withIssuer(ISSUER)
                .build();
        verifier.verify(givenToken);
    }
}