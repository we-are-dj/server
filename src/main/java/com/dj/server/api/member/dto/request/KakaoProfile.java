package com.dj.server.api.member.dto.request;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRole;
import com.dj.server.api.member.entity.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {

    private final Long id;
    private final KakaoAccount kakaoAccount;

    @Builder
    public KakaoProfile(Long id, KakaoAccount kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }

    public Member toEntity() {
        return Member.builder()
                .memberSnsId(String.valueOf(id))
                .memberRole(MemberRole.USER)
                .socialType(SocialType.KAKAO)
                .build();
    }

}
