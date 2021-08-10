package com.dj.server.common.dummy.member;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.enums.MemberRole;

/**
 *
 * 회원 더미 클래스
 *
 * 여러곳에서 사용하는 회원 데이터를 매번 코드를 작성하기에 불편함이 있어 더미 클래스를 생성하였습니다.
 *
 * 싱글톤 패턴을 활용하여 생성하였습니다.
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */
public class MemberDummy {

    private final String memberSnsId = "kakaoId123";
    private final String memberNickName = "홍길동";
    private final MemberRole memberRole = MemberRole.USER;

    private static final MemberDummy instance = new MemberDummy();

    private MemberDummy() {

    }

    public static MemberDummy getInstance() {
        if(instance == null) {
            return new MemberDummy();
        }
        return instance;
    }

    public String getMemberSnsId() {
        return memberSnsId;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public Member toEntity() {
        return Member.builder()
                .memberSnsId(memberSnsId)
                .memberNickName(memberNickName)
                .memberRole(memberRole)
                .build();
    }

}
