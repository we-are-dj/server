package com.dj.server.common.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BizException extends RuntimeException {

    private final String message;
    private final Integer errorCode;
    private final HttpStatus httpStatus;

    public BizException(ErrorCode code) {
        super(code.getMsg(), new Throwable(code.getHttpStatus().getReasonPhrase()));
        this.message = code.getMsg();
        this.errorCode = code.getErrorCode();
        this.httpStatus = code.getHttpStatus();
    }

}
