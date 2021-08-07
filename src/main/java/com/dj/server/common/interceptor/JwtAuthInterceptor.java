
package com.dj.server.common.interceptor;

import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.api.member.service.jwt.JwtUtil;
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
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    private final String ACCESS_TOKEN_KEY = "access_token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        /**
         * if (request.getHeader("memberSnsId") != null)
         * 로그인이 필요한 상황이 왔을 때, 반드시 null이 아니어야 하며 null이라면 NPE 에러가 발생하므로
         * 여기에 해당하는 예외처리가 필요할 수 있다.
         * 카카오 로그인을 당분간 사용할 계획이지만 kakao 고유아이디 (memberSnsId) 는 아직
         * header에 담아서 보내며 개발하기 힘들기 때문에, 이 때문에 발생하는 NPE 에러를 무시하기 위해 else 를 주석처리함
         *
         * @since 0.0.1
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

        jwtUtil.verifyToken(givenToken);
    }
}