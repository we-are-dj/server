package com.dj.server.api.common.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.common.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 전역 에러 핸들링
 *
 * @author informix
 * @created 2021-09-04
 * @since 0.0.1
 */
@Slf4j
@RestControllerAdvice
public class MainControllerAdvice {

    /**
     * 예상하지 못한, 예외처리하지 못한 코드에서
     * 에러가 발생할 때 동작합니다.
     *
     * @param e 전역 에러 핸들링
     * @return 500
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                                            .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                            .message(e.getMessage())
                                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                            .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handle404Exception(NoHandlerFoundException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                                            .errorCode(HttpStatus.NOT_FOUND.value())
                                            .message(e.getMessage())
                                            .httpStatus(HttpStatus.NOT_FOUND)
                                            .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * 이 서버에서 지원하지 않는 http mapping method로
     * 요청이 왔을 경우 동작합니다.
     * (예시: get만 지원하는 특정 메서드를 클라이언트가 post 방식으로 요청할 경우 동작)
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                                            .errorCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                                            .message(e.getMessage())
                                            .httpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                                            .build();

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * () -> throw new BizException(e)
     * 등의 방식으로 작성하면, 이 메서드가 이 예외처리에 대한 에러를 핸들링하게 됩니다.
     *
     * @param e throw new BizException(e)에서의 e
     * @return new BizException(e)에서 생성자로 전달된 e의 정보를 반환
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponseDTO> catchBizException(BizException e) {
        log.error(e.getMessage());

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .httpStatus(e.getHttpStatus())
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
