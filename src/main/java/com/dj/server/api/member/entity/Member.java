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

/**
 *
 *
 * 회원 Entity 클래스
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

    @Column
    private String token;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private MemberRole memberRole;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public Member(String memberSnsId, String memberNickName, MemberRole memberRole) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
        this.memberRole = memberRole;
    }

    @Builder
    public Member(String memberEmail, String memberNickName, String memberPassword, MemberRole memberRole, String token) {
        this.memberEmail = memberEmail;
        this.memberNickName = memberNickName;
        this.memberPassword = memberPassword;
        this.memberRole = memberRole;
        this.token = token;
    }

    public void updateNickName(String nickName) {
        this.memberNickName = nickName;
    }

}
