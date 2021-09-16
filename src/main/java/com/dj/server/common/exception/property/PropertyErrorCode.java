package com.dj.server.common.exception.property;

import com.dj.server.common.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PropertyErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "존재하지 않는 프로퍼티 값이 전달되었으므로 요청을 취소합니다."),
    INVALID_PROP(HttpStatus.INTERNAL_SERVER_ERROR, 500, "잘못된 프로퍼티가 전달되었습니다.");
    private final HttpStatus httpStatus;
    private final int httpErrorCode;
    private final String msg;

    PropertyErrorCode(HttpStatus httpStatus, int httpErrorCode, String msg) {
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
