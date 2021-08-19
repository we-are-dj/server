package com.dj.server.common.exception.member;

import org.springframework.http.HttpStatus;

/**
 * Member Entity의 CRUD 작업 중 발생할 수 있는 에러들을 모아둔 열거형 클래스.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
public enum MemberCrudErrorCode implements MemberErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404, "회원이 존재하지 않습니다"),
    DUPLICATED_NICKNAME(HttpStatus.FORBIDDEN, 403, "이 닉네임은 이미 사용중이기 때문에 등록할 수 없습니다.");

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
