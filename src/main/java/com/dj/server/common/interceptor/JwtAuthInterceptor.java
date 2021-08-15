
package com.dj.server.common.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import com.dj.server.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final String ACCESS_TOKEN_KEY = "access_token";
    private final String REFRESH_TOKEN_KEY = "refresh_token";
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (doesheaderhaveToken(request)) {
            String accessToken = request.getHeader(ACCESS_TOKEN_KEY);
            String refreshToken = request.getHeader(REFRESH_TOKEN_KEY);

            try {
                String newToken = jwtUtil.verifyToken(accessToken, refreshToken);
                response.setHeader(ACCESS_TOKEN_KEY, newToken);
                response.setHeader(REFRESH_TOKEN_KEY, refreshToken);
            } catch (InternalAuthenticationServiceException | JWTVerificationException | MemberException jwte) {
                log.error(jwte.getMessage());
                response.sendError(MemberPermitErrorCode.TOKEN_INVALID.httpErrorCode());
                return false;
            }

        } else if (!doesheaderhaveToken(request)) {
            log.error("로그인 중이지 않은 유저에게서 비정상적 요청이 들어왔습니다.");
            response.sendError(MemberPermitErrorCode.TOKEN_INVALID.httpErrorCode());
            return false;
        } else {
            log.error("요청을 수행하는 도중 예기치 못한 에러가 발생했습니다.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
        return true;
    }

    private boolean doesheaderhaveToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_KEY) != null &&
                request.getHeader(REFRESH_TOKEN_KEY) != null &&
                request.getHeader(ACCESS_TOKEN_KEY).length() > 2 &&
                request.getHeader(REFRESH_TOKEN_KEY).length() > 2;
    }

}