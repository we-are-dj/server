package com.dj.server.common.exception.member;

import org.springframework.http.HttpStatus;

/**
 * Member Entity의 권한 처리 중 발생할 수 있는 에러들을 모아둔 열거형 클래스.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
public enum MemberPermitErrorCode implements MemberErrorCode {
    NOT_GRANTED(HttpStatus.UNAUTHORIZED, 401, "해당 회원은 이 작업을 수행할 권한이 없습니다"),
    INVALID_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, 500, "회원에 대한 작업 수행 도중 예기치 못한 에러가 발생하였습니다"),
    SIGNIN_FAILED(HttpStatus.UNAUTHORIZED, 200, "사용자의 로그인이 실패하였습니다"),
    TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, 401, "사용자의 Token이 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String msg;
    private final Integer httpErrorCode;

    MemberPermitErrorCode(HttpStatus httpStatus, Integer httpErrorCode, String msg) {
        this.httpStatus = httpStatus;
        this.httpErrorCode = httpErrorCode;
        this.msg = msg;
    }

    public String getMsg() { return msg; }
    public Integer httpErrorCode() { return httpErrorCode;}
    public HttpStatus getHttpStatus() { return httpStatus; }
}