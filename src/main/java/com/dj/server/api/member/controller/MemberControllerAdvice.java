package com.dj.server.api.member.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.member.handler.InvalidMemberParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    /**
     * // @Valid 또는 @Validated 애너테이션을 통한 검증 실패시 동작합니다.
     * // @Valid 또는 @Validated 애너테이션을 사용하는 컨트롤러 메서드에 대한 예외 응답 처리시
     *    errors 인자값을 설정하면, 보다 자세한 에러메시지를 클라이언트에 전달할 수 있습니다.
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
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");

        return new ResponseEntity<>(response, resHeaders, HttpStatus.BAD_REQUEST);
    }
}
