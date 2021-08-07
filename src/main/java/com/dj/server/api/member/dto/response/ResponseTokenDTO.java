package com.dj.server.api.member.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ResponseTokenDTO {

    private final String refreshToken;
    private final String accessToken;

}
