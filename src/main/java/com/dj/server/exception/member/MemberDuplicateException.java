package com.dj.server.exception.member;

import javax.persistence.PersistenceException;

public class MemberDuplicateException extends Exception {

    private final MemberErrorCode errorCode;

    public MemberDuplicateException(MemberErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public MemberErrorCode getErrorCode() {
        return errorCode;
    }
}
