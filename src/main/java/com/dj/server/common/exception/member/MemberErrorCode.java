package com.dj.server.common.exception.member;

import org.springframework.http.HttpStatus;

public interface MemberErrorCode {
    String getMsg();
    Integer httpErrorCode();
    HttpStatus getHttpStatus();
}
