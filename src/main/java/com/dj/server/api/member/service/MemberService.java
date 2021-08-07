package com.dj.server.api.member.service;

import com.dj.server.api.member.dto.request.KakaoProfile;
import com.dj.server.api.member.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.api.member.service.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final JwtUtil jwtUtil;

    private final MemberRepository memberRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseTokenDTO getToken(KakaoProfile profile) {

        Member member = memberRepository.findByMemberSnsId(String.valueOf(profile.getId()))
                .map(entity -> entity.updateName(profile.getKakao_account().getProfile().getNickname()))
                .orElse(profile.toEntity());

        memberRepository.save(member);

        return createToken(member);
    }

    private ResponseTokenDTO createToken(Member member) { // 업데이트
        jwtUtil.setMember(member);

        String accessToken = jwtUtil.createAccessToken();
        String refreshToken = jwtUtil.createRefreshToken();

        member.saveRefreshToken(refreshToken);

        return new ResponseTokenDTO(accessToken, refreshToken);
    }


}
