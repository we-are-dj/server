package com.dj.server.api.musiclist.entity;


import com.dj.server.api.musiclist.dto.request.MusicListModifyRequestDTO;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.model.dto.request.PlayListModifyRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private PlayList playListId;

    @Column
    private Integer musicNo;

    @Column(length = 100)
    private String musicUrl;

    @Column(length = 100)
    private String thumbnail;

    @Column(length = 20)
    private String playtime;

    @Builder
    public MusicList(PlayList playListId, Integer musicNo, String musicUrl) {
        this.playListId = playListId;
        this.musicNo = musicNo;
        this.musicUrl = musicUrl;
    }

    public void updateMusicList(MusicListModifyRequestDTO musicListModifyRequestDTO) {
        if(musicListModifyRequestDTO.getMusicNo() != null) {
            this.musicNo = musicListModifyRequestDTO.getMusicNo();
        }

        if(musicListModifyRequestDTO.getMusicUrl() != null) {
            this.musicUrl = musicListModifyRequestDTO.getMusicUrl();
        }
    }


    public MusicList updateMusicNo(Integer musicNo) {
        this.musicNo = musicNo;
        return this;
    }

    public MusicList updateMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
        return this;
    }
}