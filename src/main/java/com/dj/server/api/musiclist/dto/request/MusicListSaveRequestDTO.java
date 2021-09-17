package com.dj.server.api.musiclist.dto.request;

import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.playlist.entity.PlayList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
@Builder
@RequiredArgsConstructor
public class MusicListSaveRequestDTO {

    @ApiModelProperty(required = true, value = "재생목록의 고유아이디")
    @NotNull
    private final Long playListId;

    @ApiModelProperty(required = true, value = "유튜브 노래 링크 주소")
    @NotNull
    private final String musicUrl;

    @ApiModelProperty(required = true, value = "유튜브 노래 썸네일")
    @NotNull
    private final String thumbnail;

    @ApiModelProperty(value = "플레이 타임")
    @NotNull
    private final String playtime;

    public MusicList toEntity(PlayList playList, Integer musicPlayOrder) {
        return MusicList.builder()
                .playList(playList)
                .musicPlayOrder(musicPlayOrder + 1)
                .musicUrl(musicUrl)
                .thumbnail(thumbnail)
                .playtime(playtime)
                .build();
    }
}
