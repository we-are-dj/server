package com.dj.server.api.member.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
    private final String ISSUER = "WE_ARE_DJ";
    private final Date ACCESS_EXPIRED_TIME = new Date(System.currentTimeMillis() / (1000 * 60 * 10)); // 10 mins
    private final Date REFRESH_EXPIRED_TIME = new Date(System.currentTimeMillis() / (1000 * 60 * 60 * 24 * 2)); // 2 weeks

    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String createAccessToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(ACCESS_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(member.getMemberId() + ""));
    }

    @Override
    public String createRefreshToken() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(REFRESH_EXPIRED_TIME)
                .sign(Algorithm.HMAC256(member.getMemberId() + ""));
    }

    @Override
    public void verifyToken(String givenToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(member.getMemberId() + ""))
                .withIssuer(ISSUER)
                .build();
        verifier.verify(givenToken);
    }
}