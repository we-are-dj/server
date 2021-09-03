package com.dj.server.common.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BizException extends RuntimeException {

    private final String message;
    private final Integer code;
    private final HttpStatus httpStatus;


    public BizException(ErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
        this.message = errorCode.getMsg();
        this.code = errorCode.httpErrorCode();
        this.httpStatus = errorCode.getHttpStatus();
    }

}
