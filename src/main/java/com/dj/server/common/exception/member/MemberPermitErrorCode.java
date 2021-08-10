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
    NOT_SIGNED(HttpStatus.UNAUTHORIZED, 401, "해당 사용자는 로그인이 되어 있지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 401, "리프레시 토큰이 만료되었습니다. 재로그인이 필요합니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, 401, "사용자의 Token이 유효하지 않습니다.");

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