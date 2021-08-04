package com.dj.server.exception.member;

import org.springframework.http.HttpStatus;

public interface MemberError {
    String getMsg();
    HttpStatus getHttpStatus();
}
