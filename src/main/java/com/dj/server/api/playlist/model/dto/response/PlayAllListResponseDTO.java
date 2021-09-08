package com.dj.server.api.playlist.model.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * 회원의 재생목록리스트를 response 로 보내주는 DTO 클래스 입니다
 *
 * @author JaeHyun
 * @create 2021-08-12
 * @since 0.0.1
 *
 */

@Getter
public class PlayAllListResponseDTO {

    //재생목록 고유 번호
    private final Long playListId;

    // 재생목록 이름
    private final String playListName;
    //사용여부
    private final String use;

    @Builder
    public PlayAllListResponseDTO(Long playListId, String playListName, String use) {
        this.playListId = playListId;
        this.playListName = playListName;
        this.use = use;
    }

}
