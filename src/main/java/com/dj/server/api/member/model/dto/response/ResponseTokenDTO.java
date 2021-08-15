package com.dj.server.api.member.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
@Builder
public class ResponseTokenDTO {

    @ApiModelProperty(required = true, value = "accessToken")
    private final String accessToken;

    @ApiModelProperty(required = true, value = "refreshToken")
    private final String refreshToken;

}
