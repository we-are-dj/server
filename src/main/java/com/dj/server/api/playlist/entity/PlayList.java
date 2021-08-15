package com.dj.server.api.playlist.entity;


import com.dj.server.api.member.entity.Member;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


/**
 * 회원의 재생목록 리스트 Entity
 * <p>
 * ex) 발라드, 팝송 , ETC
 *
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

    @NotNull
    @Column(length = 1)
    private String use;

    @Builder
    public PlayList(Member member, String playListName, String use) {
        this.member = member;
        this.playListName = playListName;
        this.use = use;
    }

    public void updatePlayListName(String playListName) {
        this.playListName = playListName;
    }

}
