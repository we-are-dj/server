package com.dj.server.api.playlist.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * 재생목록을 저장할한 데이터로 보내줄 response DTO 입니다.
 *
 *
 * @author JaeHyun
 * @create 2021-08-15
 * @since 0.0.1
 */

@Getter
@Builder
@RequiredArgsConstructor
public class PlayListSaveResponseDTO {

    private final String playListName;
    private final String use;

}
