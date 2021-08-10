package com.dj.server.api.member.dto.request;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.model.MemberRole;
import com.dj.server.api.member.model.SocialType;
import lombok.*;

/**
 * 카카오 액세스토큰을 사용하여 카카오에 유저의 정보를 요청할 떄
 * 반환되는 유저 정보를 담을 그릇으로 사용됩니다.
 *
 * @author Informix
 * @author JaeHyun
 * @created 2021-08-07
 * @since 0.0.1
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class KakaoProfile {

    private Long id;
    private KakaoAccount kakao_account;

    @Builder
    public KakaoProfile(Long id, KakaoAccount kakaoAccount) {
        this.id = id;
        this.kakao_account = kakaoAccount;
    }

    /**
     * @see com.dj.server.api.member.service.MemberService
     * @return 신규 로그인 유저 정보
     */
    public Member toEntity() {
        return Member.builder()
                .memberSnsId(String.valueOf(id))
                .memberNickName(kakao_account.getProfile().getNickname())
                .memberName(kakao_account.getProfile().getNickname())
                .memberRole(MemberRole.USER)
                .socialType(SocialType.KAKAO)
                .build();
    }

}
