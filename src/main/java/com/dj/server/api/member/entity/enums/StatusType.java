package com.dj.server.api.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * 회원의 타입을 나타내는 enum 클래스
 *
 * @author JaeHyun
 * @created 2021-08-14
 * @since 0.0.1
 */

@Getter
@RequiredArgsConstructor
public enum StatusType {

    NORMAL("정상"),
    BAN("정지");

    private final String status;

}
