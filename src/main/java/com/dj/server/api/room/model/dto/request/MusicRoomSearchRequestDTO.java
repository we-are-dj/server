package com.dj.server.api.room.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MusicRoomSearchRequestDTO {

    @ApiModelProperty(value = "방제목 검색용")
    private String roomName;

    @ApiModelProperty(required = true, value = "페이지를 보내주세요")
    @NotNull
    private Integer pageNo;

}
