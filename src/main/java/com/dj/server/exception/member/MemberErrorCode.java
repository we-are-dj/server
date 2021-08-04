package com.dj.server.exception.member;

import org.springframework.http.HttpStatus;

public interface MemberErrorCode {
    String getMsg();
    Integer httpErrorCode();
    HttpStatus getHttpStatus();
}
