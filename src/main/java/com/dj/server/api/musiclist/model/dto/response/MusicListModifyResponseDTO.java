package com.dj.server.api.musiclist.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 음악목록 정보를 변경하고, 변경된 값을 반환하는데 사용됩니다.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Getter
@Builder
@RequiredArgsConstructor
public class MusicListModifyResponseDTO {
    private final Long musicId;
    private final Integer musicPlayOrder;
    private final String musicUrl;
}
