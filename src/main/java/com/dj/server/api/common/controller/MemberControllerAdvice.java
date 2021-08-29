package com.dj.server.api.common.controller;

import com.dj.server.common.exception.common.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(BizException.class)
    public String catchMemberException(BizException error) {
        return error.getMessage();
    }

}
