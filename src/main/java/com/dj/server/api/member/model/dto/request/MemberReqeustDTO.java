package com.dj.server.api.member.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@RequiredArgsConstructor
public class MemberReqeustDTO {

    private final String nickName;
    private final String memberSnsId;

}
