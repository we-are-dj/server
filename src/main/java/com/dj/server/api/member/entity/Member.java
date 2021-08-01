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


//    private boolean memberSts;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private MemberRole memberRole;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(insertable = false, updatable = false)
    private Date creatAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(insertable = false) // default 생성이기에 insertable 설정
    private Date updateAt;

    @Builder
    public Member(String memberSnsId, String memberNickName, MemberRole memberRole) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
        this.memberRole = memberRole;
    }
}
