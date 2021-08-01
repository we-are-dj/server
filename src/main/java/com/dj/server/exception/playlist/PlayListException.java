package com.dj.server.exception.playlist;

public class PlayListException extends RuntimeException {

    public PlayListException(PlayListErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
    }
}
