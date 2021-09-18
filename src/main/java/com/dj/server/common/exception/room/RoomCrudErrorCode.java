package com.dj.server.common.exception.room;

import com.dj.server.common.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum RoomCrudErrorCode implements ErrorCode {

    Unprocessable_Entity(HttpStatus.UNPROCESSABLE_ENTITY, 422, "생성할수 있는 MusicRoom 을 초과 하였습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "해당 방을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int httpErrorCode;
    private final String msg;

    public String getMsg() {
        return msg;
    }

    public Integer getErrorCode() {
        return httpErrorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
