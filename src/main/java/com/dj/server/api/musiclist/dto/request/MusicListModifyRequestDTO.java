package com.dj.server.api.musiclist.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 클라이언트로부터 음악목록 순서 변경 요청을 받을 때 사용되는 DTO.
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
    @NotNull(message = "재생목록 정보를 변경하려면 재생목록 고유번호가 반드시 필요합니다.")
    @NotEmpty(message = "재생목록 고유번호가 비어있습니다.")
    private final Long playListId;

    @ApiModelProperty(value = "음악 목록의 고유 아이디")
    @NotNull(message = "재생목록 정보을 변경하려면 음악 목록 고유아이디 정보가 반드시 필요합니다.")
    @NotEmpty(message = "음악목록 고유 아이디 정보가 비어있습니다.")
    private final List<Long> musicIdList;

}
