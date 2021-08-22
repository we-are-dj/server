package com.dj.server.api.playlist.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 회원의 재생목록의 값들을 변경할때 필요한 파라미터들은 받는 DTO 입니다.
 *
 * @author JaeHyun
 * @create 2021-08-15
 * @since 0.0.1
 */

@Getter
@Builder
@RequiredArgsConstructor
public class PlayListModifyRequestDTO {

    @ApiModelProperty(required = true, value = "재생목록의 고유번호")
    @NotNull
    private final Long playListId;

    @ApiModelProperty(value = "변경될 재생목록 이름")
    private final String modifyPlayListName;

    @ApiModelProperty(value = "사용 여부를 변경합니다.")
    private final String use;

}
