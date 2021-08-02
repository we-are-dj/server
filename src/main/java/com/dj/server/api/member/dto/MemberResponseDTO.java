package com.dj.server.api.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberResponseDTO {

    private String nickName;
    private String memberSnsId;


    @Builder
    public MemberResponseDTO(String nickName, String memberSnsId) {
        this.nickName = nickName;
        this.memberSnsId = memberSnsId;
    }
}
