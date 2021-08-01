package com.dj.server.exception.member;

import org.springframework.http.HttpStatus;

public enum MemberErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404, "회원이 존재하지 않습니다"),
    NOT_GRANTED(HttpStatus.UNAUTHORIZED, 401, "해당 회원은 이 작업을 수행할 권한이 없습니다"),
    INVALID_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, 500, "회원에 대한 작업 수행 도중 예기치 못한 에러가 발생하였습니다"),
    DUPLICATED(HttpStatus.LOCKED, 423, "이 회원은 이미 존재합니다");

    private final HttpStatus httpStatus;
    private final String msg;

    MemberErrorCode(HttpStatus httpStatus, int error, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public String getMsg() { return msg; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}
