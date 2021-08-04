package com.dj.server.api.member.service.jwt;

import org.springframework.stereotype.Service;

/**
 * JWT token을 생성하고
 * 생성된 token을 검증하기 위하여 사용되는 인터페이스.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Service
public interface JwtUtil {
    String createToken();
    void verifyToken(String givenToken);
}