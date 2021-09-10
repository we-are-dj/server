package com.dj.server.api.musiclist.entity;


import com.dj.server.api.musiclist.dto.request.MusicListModifyRequestDTO;
import com.dj.server.api.playlist.entity.PlayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 재생될 음악리스트를 담는 엔터티
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */

@Getter
@NoArgsConstructor
@Table
@Entity
public class MusicList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PlayList playList;

    @NotNull
    @Column
    private Integer musicPlayOrder;

    @NotNull
    @Column(length = 100)
    private String musicUrl;

    @Column(length = 100)
    private String thumbnail;

    @Column(length = 20)
    private String playtime;

    @Builder
    public MusicList(PlayList playList, Integer musicPlayOrder, String musicUrl, String thumbnail, String playtime) {
        this.playList = playList;
        this.musicPlayOrder = musicPlayOrder;
        this.musicUrl = musicUrl;
        this.thumbnail = thumbnail;
        this.playtime = playtime;
    }

    public void updateMusicList(MusicListModifyRequestDTO musicListModifyRequestDTO) {

    }


    public MusicList updateMusicPlayOrder(Integer musicPlayOrder) {
        this.musicPlayOrder = musicPlayOrder;
        return this;
    }

    public MusicList updateMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
        return this;
    }
}
