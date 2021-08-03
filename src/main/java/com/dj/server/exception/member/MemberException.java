package com.dj.server.exception.member;

public class MemberException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final MemberError errorCode;

    public MemberException(MemberError errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
        this.errorCode = errorCode;
    }

    public MemberError getErrorCode() {
        return errorCode;
    }
}