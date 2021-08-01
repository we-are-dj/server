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
    private Long musicNo;
    
    @Column(length = 100)
    private String musicUrl;

}
