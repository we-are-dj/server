package com.dj.server.common.exception.common;

import org.springframework.http.HttpStatus;

public enum GeneralErrorCode implements ErrorCode {
    NOT_ALLOW_OPTIONS(HttpStatus.METHOD_NOT_ALLOWED, 405, "OPTIONS 메서드는 지원하지 않습니다."),
    NOT_ALLOW_TRACE(HttpStatus.METHOD_NOT_ALLOWED, 405, "TRACE 메서드는 지원하지 않습니다.");

    private final HttpStatus httpStatus;
    private final int httpErrorCode;
    private final String msg;

    GeneralErrorCode(HttpStatus httpStatus, int httpErrorCode, String msg) {
        this.httpStatus = httpStatus;
        this.httpErrorCode = httpErrorCode;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getErrorCode() {
        return httpErrorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
