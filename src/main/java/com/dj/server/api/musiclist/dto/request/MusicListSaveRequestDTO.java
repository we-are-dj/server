package com.dj.server.api.musiclist.dto.request;

import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.playlist.entity.PlayList;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 클라이언트로부터 음악목록 저장 요청을 받을 때 사용되는 DTO.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicListSaveRequestDTO {

    @ApiModelProperty(required = true, value = "재생목록의 고유아이디")
    @NotNull
    private Long playListId;

    @ApiModelProperty(required = true, value = "유튜브 노래 링크 주소")
    @NotNull
    private String musicUrl;

    @ApiModelProperty(required = true, value = "유튜브 노래 썸네일")
    @NotNull
    private String thumbnail;

    @ApiModelProperty(value = "플레이 타임")
    @NotNull
    private String playtime;

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
