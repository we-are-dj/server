package com.dj.server.api.common.controller;

import com.dj.server.exception.member.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(MemberException.class)
    public String catchMemberException(MemberException error) {
        return error.getMessage();
    }

}
