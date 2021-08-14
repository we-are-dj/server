package com.dj.server.api.member.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
@Builder
public class ResponseTokenDTO {

    private final String accessToken;
    private final String refreshToken;

}
