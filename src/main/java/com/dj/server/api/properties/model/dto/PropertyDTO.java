package com.dj.server.api.properties.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class PropertyDTO {
    @NotNull(message = "value 값이 요청 헤더에 포함되어 있지 않습니다.")
    @NotBlank(message = "value 값은 비어있어서는 안됩니다.")
    @ApiModelProperty(required = true, value = "인터셉터 우회 정보")
    private final String propValue;
}
