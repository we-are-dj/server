
package com.dj.server.common.interceptor;

import com.dj.server.common.exception.member.MemberPermitErrorCode;
import com.dj.server.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        final String ACCESS_TOKEN_KEY = "access_token";
        final String REFRESH_TOKEN_KEY = "refresh_token";

        if (request.getHeader(ACCESS_TOKEN_KEY) != null && request.getHeader(REFRESH_TOKEN_KEY) != null) {
            String accessToken = request.getHeader(ACCESS_TOKEN_KEY);
            String refreshToken = request.getHeader(REFRESH_TOKEN_KEY);

            String newToken = jwtUtil.verifyToken(accessToken, refreshToken);
            response.setHeader(ACCESS_TOKEN_KEY, newToken);
            response.setHeader(REFRESH_TOKEN_KEY, refreshToken);
        }
        else {
            log.error("로그인 중이지 않은 유저에게서 비정상적 요청이 들어왔습니다.");
            response.setStatus(MemberPermitErrorCode.NOT_GRANTED.httpErrorCode());
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}