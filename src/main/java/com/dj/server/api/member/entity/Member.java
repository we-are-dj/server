package com.dj.server.api.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @NotNull
    @Column(name = "member_sns_id", length = 100)
    private String memberSnsId;

    @NotNull
    @Column(name = "member_nickname", length = 45, unique = true)
    private String memberNickName;


//    private boolean memberSts;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole memberRole;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(name = "create_at", insertable = false, updatable = false)
    private Date creatAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(name = "update_at", insertable = false) // default 생성이기에 insertable 설정
    private Date updateAt;

    @Builder
    public Member(String memberSnsId, String memberNickName, MemberRole memberRole) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
        this.memberRole = memberRole;
    }
}
