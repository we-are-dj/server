package com.dj.server.api.member.repository;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.QMember;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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
    public Member invalidateRefreshToken(Long memberId) {
        final QMember member = QMember.member;
        Member wantToSignOutMember = from(member)
                                       .where(member.memberId.eq(memberId))
                                       .fetchOne();
        if (wantToSignOutMember != null) {
           return wantToSignOutMember.invalidateRefreshToken();
        }
        throw new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER);
    }
}
