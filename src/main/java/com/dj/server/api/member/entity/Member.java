package com.dj.server.api.member.entity;

import com.dj.server.api.member.model.MemberRole;
import com.dj.server.api.member.model.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @Column(length = 200)
    private String refreshToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private MemberRole memberRole;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(length = 45)
    private String memberName;

    @Builder
    public Member(String memberSnsId, String memberNickName, MemberRole memberRole, SocialType socialType, String memberName) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
        this.memberRole = memberRole;
        this.socialType = socialType;
        this.memberName = memberName;
    }

    public void updateNickName(String nickName) {
        this.memberNickName = nickName;
    }

    public Member updateName(String name) {
        this.memberName = name;
        return this;
    }

    public Member saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
