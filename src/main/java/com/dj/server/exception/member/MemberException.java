package com.dj.server.exception.member;

public class MemberException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final MemberErrorCode errorCode;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
        this.errorCode = errorCode;
    }

    public MemberErrorCode getErrorCode() {
        return errorCode;
    }
}