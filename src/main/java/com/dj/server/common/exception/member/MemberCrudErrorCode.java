package com.dj.server.common.exception.member;

import org.springframework.http.HttpStatus;

public enum MemberCrudErrorCode implements MemberErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404, "회원이 존재하지 않습니다"),
    DUPLICATED(HttpStatus.FORBIDDEN, 403, "이 회원은 이미 존재하기 때문에 재등록할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int httpErrorCode;
    private final String msg;

    MemberCrudErrorCode(HttpStatus httpStatus, int httpErrorCode, String msg) {
        this.httpStatus = httpStatus;
        this.httpErrorCode = httpErrorCode;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    public Integer httpErrorCode() { return httpErrorCode; }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
