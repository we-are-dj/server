package com.dj.server.api.member.entity;

import com.dj.server.api.member.entity.enums.MemberRole;
import com.dj.server.api.member.entity.enums.SocialType;
import com.dj.server.api.member.entity.enums.StatusType;
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
 * 회원 Entity 클래스
 *
 * @author JaeHyun
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */

@Getter
@NoArgsConstructor
@DynamicUpdate
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
    @Enumerated(EnumType.STRING)
    @Column
    private StatusType memberSts;

    @Column(length = 200, insertable = false)
    private String refreshToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private MemberRole memberRole;

    @NotNull
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
    public Member(String memberSnsId, String memberNickName, StatusType memberSts, MemberRole memberRole, SocialType socialType, String memberName) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
        this.memberSts = memberSts;
        this.memberRole = memberRole;
        this.socialType = socialType;
        this.memberName = memberName;
    }

    public Member updateName(String name) {
        this.memberName = name;
        return this;
    }

    public Member updateNickName(String nickName) {
        this.memberNickName = nickName;
        return this;
    }

    public void invalidateRefreshToken() {
        this.refreshToken = null;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
