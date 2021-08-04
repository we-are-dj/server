package com.dj.server.api.member.entity;

import java.util.Optional;

public interface MemberQueryDSLRepository {

    Member findByMemberNickName(String nickName);
    Optional<Member> findByEmail(String email);
}
