package com.dj.server.api.member.repository;

import com.dj.server.api.member.entity.Member;

import java.util.Optional;

public interface MemberQueryDSLRepository {
    Member findByMemberNickName(String nickName);
}
