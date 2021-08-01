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
    @Column(name = "streaming_room_id")
    private Long id;

    @NotNull
    @Column(name = "room_name", length = 100)
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member roomMaster;

}
