package com.dj.server.exception.playlist;

import org.springframework.http.HttpStatus;

public enum PlayListErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "플레이목록이 없습니다");

    private final HttpStatus httpStatus;
    private final String msg;

    PlayListErrorCode(HttpStatus httpStatus, int error, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public String getMsg() { return msg; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}
