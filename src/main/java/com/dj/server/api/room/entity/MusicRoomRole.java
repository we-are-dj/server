package com.dj.server.api.room.entity;


import com.dj.server.api.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Table
@Entity
public class MusicRoomRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRoleId;

    @ManyToOne
    @JoinColumn
    private MusicRoom roomId;

    @ManyToOne
    @JoinColumn
    private Member member;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private RoomRole roomRole;

}
