package com.dj.server.config.interceptor;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.api.member.service.jwt.JwtUtil;
import com.dj.server.exception.member.MemberCrudErrorCode;
import com.dj.server.exception.member.MemberException;
import com.dj.server.exception.member.MemberPermitErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class JwtAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    private final String HEADER_TOKEN_KEY = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        /**
         * if (request.getHeader("memberEmail") != null)
         * 로그인이 필요한 상황이 왔을 때, 반드시 null이 아니어야 하며 null이라면 NPE 에러가 발생하므로
         * 여기에 해당하는 예외처리가 필요할 수 있다.
         * 카카오 로그인을 당분간 사용할 계획이므로 memberEmail이라는 header는 사용되지 않기 때문에
         * header에 memberEmail이 없음으로 인해 발생하는 NPE 에러를 무시하기 위해 작성되었음.
         *
         * @since 0.0.1
         */
        if (request.getHeader("memberEmail") != null) {
            String headerEmail = request.getHeader("memberEmail");
            Member member = memberRepository.findByEmail(headerEmail)
                    .orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER));
            String givenToken = request.getHeader(HEADER_TOKEN_KEY);
            verifyToken(givenToken, member.getToken());
            Logger.getLogger("token has verified.");
        } else {
            // return false;
        }

        return true;
    }

    private void verifyToken(String givenToken, String membersToken) {
        if (!givenToken.equals(membersToken)) {
            throw new MemberException(MemberPermitErrorCode.TOKEN_MISMATCH);
        }

        jwtUtil.verifyToken(givenToken);
    }
}