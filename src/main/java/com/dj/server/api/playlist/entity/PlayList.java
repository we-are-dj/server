package com.dj.server.api.playlist.entity;


import com.dj.server.api.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 *
 * 회원의 재생목록 리스트 Entity
 *
 * ex) 발라드, 팝송 , ETC
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@Getter
@NoArgsConstructor
@Table
@Entity
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column(length = 45)
    private String playListName;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateAt;

    //private boolean use;
    @Builder
    public PlayList(Member member, String playListName) {
        this.member = member;
        this.playListName = playListName;
    }

    public void updatePlayListName(String playListName) {
        this.playListName = playListName;
    }

}
