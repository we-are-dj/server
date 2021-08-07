/*

package com.dj.server.api.member.service;

import com.dj.server.api.member.dto.request.KakaoProfile;
import com.dj.server.api.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        KakaoProfile kakaoProfile = (KakaoProfile) session.getAttribute("카카오에서 반환된 프로릴");
        return memberService.getMember(kakaoProfile);
    }
}
*/