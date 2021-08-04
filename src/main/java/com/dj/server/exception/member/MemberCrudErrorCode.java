package com.dj.server.exception.member;

import org.springframework.http.HttpStatus;

public enum MemberCrudErrorCode implements MemberError {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404, "회원이 존재하지 않습니다"),
    DUPLICATED(HttpStatus.LOCKED, 423, "이 회원은 이미 존재하기 때문에 재등록할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int error;
    private final String msg;

    MemberCrudErrorCode(HttpStatus httpStatus, int error, String msg) {
        this.httpStatus = httpStatus;
        this.error = error;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
