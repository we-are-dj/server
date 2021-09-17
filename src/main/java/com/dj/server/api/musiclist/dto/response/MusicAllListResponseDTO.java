package com.dj.server.api.musiclist.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 데이터베이스에 저장된 음악목록 정보를 반환하는데 사용됩니다.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Getter
@Builder
@RequiredArgsConstructor
public class MusicAllListResponseDTO {
    private final Long musicId;
    private final Integer musicPlayOrder;
    private final String musicUrl;
}
