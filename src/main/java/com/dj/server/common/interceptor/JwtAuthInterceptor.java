
package com.dj.server.common.interceptor;

import com.dj.server.common.jwt.JwtUtil;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        final String ACCESS_TOKEN_KEY = "access_token";
        final String REFRESH_TOKEN_KEY = "refresh_token";

        if (request.getHeader(ACCESS_TOKEN_KEY) != null && request.getHeader(REFRESH_TOKEN_KEY) != null) {
            String accessToken = request.getHeader(ACCESS_TOKEN_KEY);
            String refreshToken = request.getHeader(REFRESH_TOKEN_KEY);

            String newToken = jwtUtil.verifyAccessToken(accessToken, refreshToken);
            response.setHeader(ACCESS_TOKEN_KEY, newToken);
        } /*else {
            log.error("해당 사용자는 로그인 유지에 필요한 토큰 값을 가지고 있지 않습니다.");
            return false;
            //throw new MemberException(MemberPermitErrorCode.NOT_SIGNED);
        }*/
        return true;
    }
}