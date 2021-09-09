package com.dj.server.api.playlist.entity;


import com.dj.server.api.member.entity.Member;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.dj.server.api.playlist.model.dto.request.PlayListModifyRequestDTO;
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
    @JoinColumn(name = "member_id")
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

    public void updatePlayList(PlayListModifyRequestDTO playListModifyRequestDTO) {

        if(playListModifyRequestDTO.getModifyPlayListName() != null) {
            this.playListName = playListModifyRequestDTO.getModifyPlayListName();
        }

        if(playListModifyRequestDTO.getUse() != null) {
            this.use = playListModifyRequestDTO.getUse();
        }
    }

    //사용안함으로 변경합니다.
    public PlayList updateUseNo() {
        this.use = "N";
        return this;
    }



}
