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
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotNull
    @Column(length = 100, columnDefinition = "sns 고유아이디입니다.")
    private String memberSnsId;

    @NotNull
    @Column(length = 45, unique = true, columnDefinition = "서비스에서 사용되는 닉네임 고유값이기에 유니크")
    private String memberNickName;


//    private boolean memberSts;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "회원의 권한 컬럼 입니다.")
    private MemberRole memberRole;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(insertable = false, updatable = false , columnDefinition = "가입 날짜 default 로 NOW() 를 주고 update 시 변경이 안되게 설정")
    private Date creatAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(insertable = false , columnDefinition = "닉네임이나 회원 정보가 변경되면 업데이트 될 값 default NOW() db 설정 insert false") // default 생성이기에 insertable 설정
    private Date updateAt;

    @Builder
    public Member(String memberSnsId, String memberNickName) {
        this.memberSnsId = memberSnsId;
        this.memberNickName = memberNickName;
    }
}
