package com.dj.server.common.exception.common;

import com.dj.server.common.exception.member.MemberErrorCode;

public class BizException extends RuntimeException {

    private final ErrorCode errorCode;


    public BizException(ErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
        this.errorCode = errorCode;
    }

}
