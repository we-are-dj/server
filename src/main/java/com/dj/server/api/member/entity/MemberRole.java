package com.dj.server.api.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Member 권한 구분
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Getter
@RequiredArgsConstructor
public enum MemberRole {
    ADMIN, USER
}
