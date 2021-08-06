package com.dj.server.api.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class MemberTestRequestDTO {

    private String nickName;
    private String memberSnsId;


    @Builder
    public MemberTestRequestDTO(String nickName, String memberSnsId) {
        this.nickName = nickName;
        this.memberSnsId = memberSnsId;
    }

}
