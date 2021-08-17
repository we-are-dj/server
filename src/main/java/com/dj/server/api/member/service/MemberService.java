package com.dj.server.api.member.service;

import com.dj.server.api.member.model.dto.request.MemberSaveRequestDTO;
import com.dj.server.api.member.service.oauth2.kakao.request.KakaoRequest;
import com.dj.server.api.member.model.vo.kakao.KakaoProfile;
import com.dj.server.api.member.model.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.model.vo.kakao.KakaoToken;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저에 대한 전반적인 비즈니스 로직을 담당합니다.
 * oauth2로부터 인증된 회원의 정보 가져오기, 회원 정보를 DB에 저장 등을 처리합니다.
 *
 * @author Informix
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final KakaoRequest kakaoRequest;

    /**
     * 카카오로 로그인한 유저 정보 중 카카오의 고유 ID를 확인하여
     *
     * if (예전에 로그인했던 유저라면)
     *    Database에 이미 고유 ID가 저장되어 있으므로
     *    유저가 카카오에서 변경한 닉네임으로 Database에 갱신합니다.
     *
     * else if (신규 로그인 유저라면)
     *    카카오부터 받은 유저정보를 모두 Database에 저장합니다.
     *
     * @created 0.0.1
     * @param profile 카카오에서 제공하는 액세스 토큰을 사용하여
     * @return 서버에서 생성한 액세스 토큰과 리프레시 토큰
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseTokenDTO getGeneratedTokens(KakaoProfile profile) {

        Member member = memberRepository.findByMemberSnsId(String.valueOf(profile.getId()))
                .map(entity -> entity.updateName(profile.getKakao_account().getProfile().getNickname()))
                .orElse(profile.toEntity());

        memberRepository.save(member);

        return createToken(member);
    }

    /**
     *  Member의 고유 Id를 사용하여 토큰들을 생성합니다.
     * 액세스토큰과 리프레시토큰을 생성 후 리프레시토큰은 데이터베이스에 저장하고,
     * 프론트엔드 서버로 이 토큰들을 반환하고자 하는 용도로 사용됩니다.
     *
     * @param member Database에 저장된 Member 정보
     * @return 서버에서 생성한 액세스 토큰과 리프레시 토큰
     */

    private ResponseTokenDTO createToken(Member member) {

        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));
        String accessToken = jwtUtil.createAccessToken();
        String refreshToken = jwtUtil.createRefreshToken();

        member.saveRefreshToken(refreshToken);

        return ResponseTokenDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
    }


    /**
     * 먼저 인가코드를 사용하여 카카오로부터 액세스 토큰을 발급받습니다.
     * 그 후 액세스토큰을 사용하여 로그인한 유저의 정보를 카카오로부터 가져옵니다.
     *
     * 이 작업은 KakaoProfile의 getKakaoProfile 메서드에
     * 카카오 프로필을 가져오는 것을 위임하여 수행되며,
     * KakaoProfile의 getKakaoProfile 메서드가 반환하는 결과값을 리턴합니다.
     *
     * @see KakaoProfile
     * @param memberSaveRequestDTO : code 카카오 인가코드, uri redirect uri
     * @return 카카오 액세스 토큰을 사용하여 카카오로부터 받은 유저의 프로필 정보
     */
    public KakaoProfile getKakaoProfile(MemberSaveRequestDTO memberSaveRequestDTO) {
        KakaoToken kakaoToken = kakaoRequest.getKakaoAccessToken(memberSaveRequestDTO.getCode(), memberSaveRequestDTO.getRedirectUri());
        return kakaoRequest.getKakaoProfile(kakaoToken);
    }

    /**
     * 액세스 토큰의 페이로드를 복호화할 때, 그 안에 포함되었던 memberId를
     * JwtUtil은 저장해두고 있습니다. 그 값을 가져옵니다.
     *
     * @return member 고유 아이디
     */
    public Long getMemberId() {
        return jwtUtil.getMemberId();
    }

    /**
     * 로그아웃 요청시 데이터베이스의 리프레시 토큰을 null로 만들어 만료시킵니다.
     *
     * @since 0.0.1
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void invalidateRefreshToken() {
        memberRepository.findById(getMemberId()).orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER)).invalidateRefreshToken();
    }
}
