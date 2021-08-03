package com.dj.server.exception.member;

import org.springframework.http.HttpStatus;

/**
 * Member Entity에 대한 데이터 요청시 발생할 수 있는 에러들을 열거형으로 모아둔 클래스
 *
 * @see MemberException class
 * @author Informix
 * @created 2021-08-03 Tue
 */
public enum MemberErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404, "회원이 존재하지 않습니다"),
    NOT_GRANTED(HttpStatus.UNAUTHORIZED, 401, "해당 회원은 이 작업을 수행할 권한이 없습니다"),
    INVALID_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, 500, "회원에 대한 작업 수행 도중 예기치 못한 에러가 발생하였습니다"),
    DUPLICATED(HttpStatus.LOCKED, 423, "이 회원은 이미 존재하기 때문에 재등록할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String msg;

    /**
     * MemberErrorCode의 생성자
     *
     * @param httpStatus spring의 HttpStatus에 등록되어 있는 http 통신에러코드들 중 하나를
     *                   MemberErrorCode와 매핑하여, MemberErrorCode에 있는 에러가 실제로 어떤 http 통신 실패를 했는지 알려주는 목적으로 사용함
     * @param error Http 통신코드번호를 기술하여 가독성을 향상함
     * @param msg 에러 발생시 정확한 원인을 전달하기 위하여 기술함
     */
    MemberErrorCode(HttpStatus httpStatus, int error, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    /**
     * @return 정확한 에러 발생 원인 메시지
     */
    public String getMsg() { return msg; }

    /**
     * @return MemberErrorCode와 매핑된 HttpStatus 통신 실패 코드 열거형 타입
     */
    public HttpStatus getHttpStatus() { return httpStatus; }
}
