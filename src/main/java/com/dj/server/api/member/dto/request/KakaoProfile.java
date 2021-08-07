package com.dj.server.api.member.dto.request;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRole;
import com.dj.server.api.member.entity.SocialType;
import lombok.*;

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
