package com.dj.server.api.member.entity;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;


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

    @Override
    public Optional<Member> findByEmail(String email) {
        final QMember member = QMember.member;

        return Optional.ofNullable(from(member)
                .where(member.memberEmail.eq(email))
                .fetchOne());
    }
}
