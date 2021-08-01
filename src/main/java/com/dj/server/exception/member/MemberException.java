package com.dj.server.exception.member;

public class MemberException extends RuntimeException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
    }
}