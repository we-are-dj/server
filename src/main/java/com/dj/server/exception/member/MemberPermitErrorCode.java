package com.dj.server.exception.member;

import org.springframework.http.HttpStatus;

public enum MemberPermitErrorCode implements MemberError {
    NOT_GRANTED(HttpStatus.UNAUTHORIZED, 401, "해당 회원은 이 작업을 수행할 권한이 없습니다"),
    INVALID_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, 500, "회원에 대한 작업 수행 도중 예기치 못한 에러가 발생하였습니다");

    private final HttpStatus httpStatus;
    private final String msg;

    MemberPermitErrorCode(HttpStatus httpStatus, int error, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public String getMsg() { return msg; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}