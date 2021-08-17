
package com.dj.server.common.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;

import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import com.dj.server.common.jwt.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final String TOKEN_KEY = "dj_token";
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (!doesHeaderhaveToken(request)) {
            log.error("로그인 중이지 않은 유저에게서 비정상적 요청이 들어왔습니다.");
            response.sendError(MemberPermitErrorCode.TOKEN_INVALID.httpErrorCode());
            return false;
        }

        try {
            String token = request.getHeader(TOKEN_KEY);
            String newToken = jwtUtil.verifyToken(token);
            response.setHeader(TOKEN_KEY, newToken);
        } catch (InternalAuthenticationServiceException | JWTVerificationException | MemberException jwte) {
            log.error(jwte.getMessage());
            response.sendError(MemberPermitErrorCode.TOKEN_INVALID.httpErrorCode());
            return false;
        }

        return true;
    }

    private boolean doesHeaderhaveToken(HttpServletRequest request) {
        return request.getHeader(TOKEN_KEY) != null || request.getHeader(TOKEN_KEY).length() > 4; // jwt needs 5 words at least. exampl: f.s.a
    }

}