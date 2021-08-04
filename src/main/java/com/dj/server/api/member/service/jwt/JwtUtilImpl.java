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
    private final String TEST_SIGN_KEY = "TESTKEY";
    private final String ISSUER = "TEST_ISSUER";
    private final Date EXPIRED_TIME = new Date((System.currentTimeMillis() / 1000 / 60) * 10); // 10 mins

    @Override
    public String createToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(EXPIRED_TIME)
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