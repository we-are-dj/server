package com.dj.server.api.playlist.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원의 재생목록의 값들을 변경할고 변경된 값을 보내줄 DTO 입니다.
 *
 * @author JaeHyun
 * @create 2021-08-15
 * @since 0.0.1
 */

@Getter
@Builder
@RequiredArgsConstructor
public class PlayListModifyResponseDTO {

    private final Long playListId;

    private final String playListName;

    private final String use;

}
