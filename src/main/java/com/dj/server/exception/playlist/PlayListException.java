package com.dj.server.exception.playlist;

public class PlayListException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final PlayListErrorCode errorCode;

    public PlayListException(PlayListErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
        this.errorCode = errorCode;
    }

    public PlayListErrorCode getErrorCode() {
        return errorCode;
    }
}
