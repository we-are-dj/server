package com.dj.server.api.musiclist.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 클라이언트로부터 음악목록 삭제 요청을 받을 때 사용되는 DTO.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */

@Getter
@RequiredArgsConstructor
public class MusicListDeleteRequestDTO {
    @ApiModelProperty(required = true, value = "삭제할 음악목록의 재생목록 번호를 보내주세요")
    @NotNull
    private final Long playListId;

    @ApiModelProperty(required = true, value = "삭제할 음악목록 고유번호를 보내주세요")
    @NotNull
    private final Long musicId;
}
