package com.dj.server.api.member.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.member.InvalidMemberParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    /**
     * // @Valid 애너테이션을 통한 검증 실패시 동작합니다.
     * // @Valid 애너테이션을 사용하는 컨트롤러 메서드에 대한 예외처리시
     *    반드시 errors 인자값을 주어야 합니다.
     *
     * @param e InvalidParameterException
     * @return 400 (Bad Request: invalid parameter error)
     */
    @ExceptionHandler(InvalidMemberParameterException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidMemberParameterException(InvalidMemberParameterException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                                .message(e.getMessage())
                                                .errors(e.getErrors())
                                                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
