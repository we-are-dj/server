package com.dj.server.api.musiclist.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 클라이언트로부터 음악목록 정보 변경 요청을 받을 때 사용되는 DTO.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Getter
@Builder
@RequiredArgsConstructor
public class MusicListModifyRequestDTO {

    @ApiModelProperty(required = true, value = "플레이리스트의 고유번호")
    @NotNull
    private final Long playListId;

    @ApiModelProperty(required = true, value = "음악목록의 고유번호")
    @NotNull
    private final Long musicId;

    @ApiModelProperty(value = "변경될 음악 플레이 순서")
    private final Integer musicNo;

}
