package com.dj.server.api.musiclist.dto.request;

import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.playlist.entity.PlayList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 클라이언트로부터 음악목록 저장 요청을 받을 때 사용되는 DTO.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */

@Getter
@RequiredArgsConstructor
public class MusicListSaveRequestDTO {

    @ApiModelProperty(required = true, value = "재생 순서")
    @NotNull
    private final Integer musicNo;

    public MusicList toEntity(PlayList playList) {
        return MusicList.builder()
                .playListId(playList)
                .musicNo(musicNo)
                .build();
    }

}
