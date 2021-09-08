package com.dj.server.api.playlist.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 *
 * 회원의 모든 재생목록을 조회할때 요청 파라매터를 담는 DTO 입니다
 *
 *
 * @author JaeHyun
 * @create 2021-08-15
 * @since 0.0.1
 */

@Getter
@RequiredArgsConstructor
public class PlayListDeleteRequestDTO {


    @ApiModelProperty(required = true, value = "삭제할 재생목록 고유번호를 보내주세요")
    @NotNull
    private Long playListId;

}
