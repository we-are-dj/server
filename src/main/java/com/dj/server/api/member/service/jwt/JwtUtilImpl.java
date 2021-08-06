package com.dj.server.api.member.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * JwtUtil의 구현체로서 token 생성시의 규칙 및
 * token이 유효한지를 검증합니다.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Repository
public class JwtUtilImpl implements JwtUtil {
    private final String TEST_SIGN_KEY = "TEST_KEY";
    private final String ISSUER = "WE_ARE_DJ";
    private final Date ACCESS_EXPIRED_TIME = new Date(System.currentTimeMillis() / (1000 * 60 * 10)); // 10 mins
    private final Date REFRESH_EXPIRED_TIME = new Date(System.currentTimeMillis() / (1000 * 60 * 60 * 24 * 2)); // 2 weeks

    @Override
    public String createAccessToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(ACCESS_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(TEST_SIGN_KEY));
    }

    @Override
    public String createRefreshToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(REFRESH_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(TEST_SIGN_KEY));
    }

    @Override
    public void verifyToken(String givenToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TEST_SIGN_KEY))
                .withIssuer(ISSUER)
                .build();
        verifier.verify(givenToken);
    }
}