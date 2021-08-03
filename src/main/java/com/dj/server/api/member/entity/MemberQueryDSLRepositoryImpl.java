package com.dj.server.api.member.entity;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


public class MemberQueryDSLRepositoryImpl extends QuerydslRepositorySupport implements MemberQueryDSLRepository {

    public MemberQueryDSLRepositoryImpl() {
        super(Member.class);
    }


    @Override
    public Member findByMemberNickName(String nickName) {
        final QMember member = QMember.member;

        return from(member)
                .where(member.memberNickName.eq(nickName))
                .fetchOne();

    }
}
