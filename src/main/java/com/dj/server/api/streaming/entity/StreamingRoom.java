package com.dj.server.api.streaming.entity;


import com.dj.server.api.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
@Table(name = "streaming_room")
@Entity
public class StreamingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100 , columnDefinition = "스트리밍 방 제목입니다.")
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", columnDefinition = "회원 아이디 FK")
    private Member roomMaster;

}
