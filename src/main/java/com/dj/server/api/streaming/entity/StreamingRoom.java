package com.dj.server.api.streaming.entity;


import com.dj.server.api.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 *
 * 스트리밍 방에 대한 Entity
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 *
 */

@Getter
@NoArgsConstructor
@Table
@Entity
public class StreamingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member roomMaster;

}
