package com.dj.server.common.exception.common;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMsg();
    Integer getErrorCode();
    HttpStatus getHttpStatus();
}
