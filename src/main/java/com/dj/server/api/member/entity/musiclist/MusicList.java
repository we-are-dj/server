package com.dj.server.api.member.entity.musiclist;


import com.dj.server.api.member.entity.playlist.MemberPlayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;

/**
 * 재생목록에 대한 노래를 수록하는 Entity
 *
 *
 * @author JaeHyun
 * @created 2021-08-04
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
    private MemberPlayList memberPlayList;

    @Column
    private Integer musicNo;

    @Column(length = 100)
    private String musicUrl;

    @Builder
    public MusicList(Integer musicNo, String musicUrl) {
        this.musicNo = musicNo;
        this.musicUrl = musicUrl;
    }
}
