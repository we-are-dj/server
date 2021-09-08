package com.dj.server.api.room.entity;

import com.dj.server.api.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * 뮤직 룸에 대한 접속 유저 정보를 저장하는 테이블
 * 유저 수를 계산합니다.
 * @see MusicRoom
 * @see Member
 *
 * @author JaeHyun
 * @created 2021-09-08
 * @since 0.0.1
 *
 */

@Getter
@NoArgsConstructor
@Table
@Entity
public class AccessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private MusicRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @NotNull
    @Column(length = 60)
    private String sessionId;




}
