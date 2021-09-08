package com.dj.server.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDTO {

    private final String nickName;
    private final String memberSnsId;


    @Builder
    public MemberResponseDTO(String nickName, String memberSnsId) {
        this.nickName = nickName;
        this.memberSnsId = memberSnsId;
    }
}
