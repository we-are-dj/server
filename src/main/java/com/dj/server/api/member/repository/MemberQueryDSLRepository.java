package com.dj.server.api.member.repository;

import com.dj.server.api.member.entity.Member;

public interface MemberQueryDSLRepository {
    Member findByMemberNickName(String nickName);

}
