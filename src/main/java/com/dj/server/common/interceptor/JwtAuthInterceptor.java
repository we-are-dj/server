
package com.dj.server.common.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dj.server.api.properties.service.PropertyService;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.member.enums.MemberPermitErrorCode;
import com.dj.server.common.filter.CountryFilter;
import com.dj.server.common.jwt.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 클라이언트가 전달하는 json web token이 유효한지 검증하는 클래스.
 *
 * @author informix
 * @created 2021-08-07
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final String ACCESS_TOKEN_KEY = "access_token";
    private final String REFRESH_TOKEN_KEY = "refresh_token";
    private final JwtUtil jwtUtil;
    private final PropertyService propertyService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
         if (doesHeaderhaveProp(request)) {
             if (propertyService.doesPropValExist(request.getHeader("prop_value"))) {
                jwtUtil.setTokenIngredient(request.getHeader("memberId"));
                return true;
            } else return false;
        }

        if (!doesHeaderhaveToken(request)) {
            log.error("로그인 중이지 않은 유저에게서 비정상적 요청이 들어왔습니다.");
            log.error("catch ip: " + CountryFilter.getClientIp(request));
            throw new BizException(MemberPermitErrorCode.TOKEN_INVALID);
        }

        try {
            String accessToken = request.getHeader(ACCESS_TOKEN_KEY);
            String refreshToken = request.getHeader(REFRESH_TOKEN_KEY);
            String newAccessToken = jwtUtil.verifyToken(accessToken, refreshToken);
            response.addHeader(ACCESS_TOKEN_KEY, newAccessToken);
        } catch (InternalAuthenticationServiceException | JWTVerificationException | BizException jwte) {
            log.error(jwte.getMessage());
            throw new BizException(MemberPermitErrorCode.TOKEN_INVALID);
        }

        return true;
    }

    private boolean doesHeaderhaveToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_KEY) != null || request.getHeader(REFRESH_TOKEN_KEY) != null;
    }

    private boolean doesHeaderhaveProp(HttpServletRequest request) {
        return request.getHeader("prop_value") != null && request.getHeader("memberId") != null;
    }

}