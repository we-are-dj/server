package com.dj.server.api.member.entity.playlist;


import com.dj.server.api.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;


/**
 *
 * 회원의 플레이 리스트 Entity
 *
 * ex) 발라드, 팝송 , ETC
 *
 */

@Getter
@NoArgsConstructor
@Entity
public class MemberPlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", columnDefinition = "회원 ID를 FK로 받습니다.")
    private Member member;

    @Column(length = 45, columnDefinition = "플레이 리스트 이름입니다, ex) 팝송, 발라드")
    private String playListName;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(insertable = false, updatable = false , columnDefinition = "생성 날짜 입니다.")
    private Date creatAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(insertable = false , columnDefinition = "업데이트 날짜 입니다.") // default 생성이기에 insertable 설정
    private Date updateAt;

    //private boolean use;


}
