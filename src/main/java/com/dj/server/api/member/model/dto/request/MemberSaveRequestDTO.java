package com.dj.server.api.member.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@RequiredArgsConstructor
public class MemberSaveRequestDTO {

    @ApiModelProperty(required = true, value = "카카오 인가 코드", example = "QCXPAXPAS...")
    private final String code;

    @ApiModelProperty(required = true, value = "카카오 RedirectURI", example = "wearedj.club/signin")
    private final String redirectUri;

}
