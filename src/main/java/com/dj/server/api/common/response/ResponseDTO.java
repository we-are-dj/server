package com.dj.server.api.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ResponseDTO<T> {

    private final T data;
    private final String message;

}
