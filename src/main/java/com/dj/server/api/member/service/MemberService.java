package com.dj.server.api.member.service;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.api.member.entity.SocialType;
import com.dj.server.api.member.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 유저에 대한 전반적인 비즈니스 로직을 담당합니다.
 * oauth2 인증 회원 정보 가져오기, 회원 정보를 DB에 저장 등을 처리합니다.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    protected Member getMember(Member member, HttpSession session) {
        if (member != null) {
            try {
                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = token.getPrincipal().getAttributes();

                Member findMember = memberRepository.findByMemberSnsId(member.getMemberSnsId());
                if (findMember == null)
                    member = memberRepository.save(member);

                session.setAttribute("member", member);
            } catch (ClassCastException ex) {
                return member;
            }
        }
        return member;
    }

    private Member getKaKaoProfile(Long kakaoId, Map<String, Object> map) {
        Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");

        return Member.builder()
                .memberSnsId(String.valueOf(kakaoId))
                .memberNickName(propertyMap.get("nickname"))
                .socialType(SocialType.KAKAO)
                .build();
    }
}
