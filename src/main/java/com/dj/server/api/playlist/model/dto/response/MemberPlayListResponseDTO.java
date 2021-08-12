package com.dj.server.api.playlist.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 회원의 재생목록들을 response로 보내주는 DTO 클래스 입니다
 *
 * @author JaeHyun
 * @create 2021-08-12
 * @since 0.0.1
 *
 */

@Getter
@NoArgsConstructor
public class MemberPlayListResponseDTO {
    
    // 재생목록 이름
    private String playListName;
    
    // 사용 여부
    private boolean use;

    @Builder
    public MemberPlayListResponseDTO(String playListName, boolean use) {
        this.playListName = playListName;
        this.use = use;
    }
}
