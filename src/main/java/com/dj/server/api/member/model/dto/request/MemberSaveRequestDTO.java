package com.dj.server.api.member.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
public class MemberSaveRequestDTO {

    @NotNull(message = "카카오 인가 코드는 필수입니다. 인가코드를 요청값에 'code'로 담아서 보내주세요.")
    @NotBlank(message = "카카오 인가 코드는 필수입니다. 값이 비어있어서는 안됩니다.")
    @ApiModelProperty(required = true, value = "카카오 인가 코드", example = "QCXPAXPAS...")
    private final String code;

    @NotNull(message = "반드시 redirect uri를 함께 보내주셔야 합니다. uri를 요청시 'redirectUri'로 담아서 보내주세요.")
    @NotBlank(message = "카카오 인가 코드는 필수입니다. 값이 비어있어서는 안됩니다.")
    @ApiModelProperty(required = true, value = "카카오 RedirectURI", example = "wearedj.club/signin")
    private final String redirectUri;
}
