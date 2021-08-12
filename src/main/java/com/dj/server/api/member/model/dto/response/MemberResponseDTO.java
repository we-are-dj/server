package com.dj.server.api.member.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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
