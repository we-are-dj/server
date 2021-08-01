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
@Table(name = "music_list")
@Entity
public class MusicList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_list_id")
    private MemberPlayList memberPlayList;
    
    @Column(name = "music_no")
    private Long musicNo;
    
    @Column(name = "music_url", length = 100)
    private String musicUrl;

}
