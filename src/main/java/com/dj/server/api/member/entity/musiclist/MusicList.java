package com.dj.server.api.member.entity.musiclist;


import com.dj.server.api.member.entity.playlist.MemberPlayList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *
 * 플레이 리스트에 대한 노래 수록
 *
 */

@Getter
@NoArgsConstructor
@Entity
public class MusicList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", columnDefinition = "플레이 리스트를 FK로 받습니다.")
    private MemberPlayList memberPlayList;
    
    @Column(columnDefinition = "노래 순서입니다.")
    private Long musicNo;
    
    @Column(length = 100, columnDefinition = "유튜브 링크 주소")
    private String musicUrl;

}
