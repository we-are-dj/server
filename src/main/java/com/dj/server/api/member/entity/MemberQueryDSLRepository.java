package com.dj.server.api.member.entity;

public interface MemberQueryDSLRepository {

    Member findByMemberNickName(String nickName);

}
