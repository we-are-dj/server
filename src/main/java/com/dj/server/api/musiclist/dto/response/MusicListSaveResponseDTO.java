package com.dj.server.api.musiclist.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 음악목록을 저장하고, 저장된 값을 반환하는데 사용됩니다.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */

@Getter
@Builder
@RequiredArgsConstructor
public class MusicListSaveResponseDTO {
    private final Long musicId;
    private final Integer musicNo;
    private final String musicUrl;
    private final String thumbnail;
    private final String playtime;
}
