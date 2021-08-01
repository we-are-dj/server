package com.dj.server.api.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotNull
    @Column(length = 100)
    private String memberSnsId;

    @NotNull
    @Column(length = 45, unique = true)
    private String memberNickName;


    @NotNull
    @ColumnDefault("0")
    @Column
    private boolean memberSts;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private MemberRole memberRole;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public Member(String memberSnsId, String memberNickName, MemberRole memberRole) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
        this.memberRole = memberRole;
    }

    public void updateNickName(String nickName) {
        this.memberNickName = nickName;
    }

}
