package com.dj.server.api.member.entity.playlist;


import com.dj.server.api.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
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
@Table(name = "member_play_list")
@Entity
public class MemberPlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 45)
    private String playListName;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(name = "create_at", insertable = false, updatable = false)
    private Date creatAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @ColumnDefault("NOW()")
    @Column(name = "update_at", insertable = false) // default 생성이기에 insertable 설정
    private Date updateAt;

    //private boolean use;


}
