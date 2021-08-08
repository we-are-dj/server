
package com.dj.server.common.interceptor;

import com.dj.server.common.jwt.JwtClass;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtClass jwtClass;

    // private final String ACCESS_TOKEN_KEY = "access_token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        /**
         *  if (request.getHeader(ACCESS_TOKEN_KEY) != null)
         *  로그인된 유저일 경우 해당 조건문을 수행.
         *
         *  로그인이 되어 있지 않다면 로그인을 해달라고 요청해야 한다.
         *
         *  @since 0.0.1
         */
       /* if (request.getHeader(ACCESS_TOKEN_KEY) != null) {
            String accessToken = request.getHeader(ACCESS_TOKEN_KEY);
            jwtUtil.verifyToken(accessToken);
            return true;
        } else {
            // log.error("해당 사용자는 로그인이 되어 있지 않음");
            // throw new MemberException(MemberPermitErrorCode.NOT_GRANTED);
            // return false;
        }
        */
        return true;
    }

    private void verifyRefreshToken(String givenToken, String membersToken) {
        if (!givenToken.equals(membersToken)) {
            throw new MemberException(MemberPermitErrorCode.TOKEN_MISMATCH);
        }

        jwtClass.verifyToken(givenToken);
    }
}