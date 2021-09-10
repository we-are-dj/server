package com.dj.server.api.musiclist.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @ApiModelProperty(required = true, value = "삭제할 음악목록 고유번호를 보내주세요.")
    @NotEmpty(message = "삭제할 음악의 고유번호가 전달되지 않았습니다.")
    private final List<Long> musicIdList;
}
