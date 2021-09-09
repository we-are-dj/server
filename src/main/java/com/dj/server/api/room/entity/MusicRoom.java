package com.dj.server.api.room.entity;


import com.dj.server.api.member.entity.Member;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * 음악방에 대한 Entity
 *
 * 음악방에 대항 정보를 담고 있습니다
 * @see Member
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@Getter
@NoArgsConstructor
@Table
@Entity
public class MusicRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @NotNull
    @Column(length = 100)
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column
    private Integer roomUserCount;

    @Builder
    public MusicRoom(String roomName, Member member, Integer roomUserCount) {
        this.roomName = roomName;
        this.member = member;
        this.roomUserCount = roomUserCount;
    }
}
