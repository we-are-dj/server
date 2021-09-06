package com.dj.server.api.common.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.filter.CountryFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        return handleGeneralException(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    /**
     * 잘못된 인수가 포함된 요청이 왔을 경우 처리합니다.
     * 예시: StrangeProtocol://server.wearedj.club/...
     * 예시2: "http://"가 없는 server.wearedj.club/ 요청
     *
     * @return 400
     */
    @ExceptionHandler({IllegalArgumentException.class, MalformedURLException.class})
    protected ResponseEntity<ErrorResponseDTO> handleIllgegalURLException(IllegalArgumentException iae, MalformedURLException mue) {
        return handleGeneralException(HttpStatus.BAD_REQUEST, iae, mue);
    }

    /**
     * 404 에러가 발생할 경우 기본 지정된 에러페이지로 리다이렉트시킵니다.
     *
     * 반드시 아래의 설정을 yml 또는 properties에 해두어야 정상적으로 동작합니다.
     * spring.mvc.throw-exception-if-no-handler-found: true
     * spring.web.resources.add-mappings: false
     *
     * @return 404 뷰페이지
     */
    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    protected ModelAndView handle404(HttpServletRequest request) {
        log.error("어떤 유저가 존재하지 않는 url로 자원을 요청했습니다.");
        log.error("catch ip: " + CountryFilter.getClientIp(request));
        return new ModelAndView("redirect:/errors/404.html", HttpStatus.NOT_FOUND);
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
        return handleGeneralException(HttpStatus.METHOD_NOT_ALLOWED, e);
    }

    /**
     * 프로토콜 비정상 에러 처리
     * 잘못된 프로토콜 연결을 시도했거나 프로토콜 연결에 장애가 발생했을 때 동작합니다.
     *
     * @param e BadGateway 502
     * @return 502
     */
    @ExceptionHandler(HttpServerErrorException.BadGateway.class)
    protected ResponseEntity<ErrorResponseDTO> handleBadGateway(HttpServerErrorException.BadGateway e) {
        return handleGeneralException(HttpStatus.BAD_GATEWAY, e);
    }

    /**
     * 서버 접속자가 많아서 서버가 처리하지 못하고 있거나
     * 서버에 장애가 발생하여 요청을 정상 처리하지 못할 경우 동작합니다.
     *
     * @param e Service Unavailable 503
     * @return 503
     */
    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    protected ResponseEntity<ErrorResponseDTO> handleServiceUnavailable(HttpServerErrorException.ServiceUnavailable e) {
        return handleGeneralException(HttpStatus.SERVICE_UNAVAILABLE, e);
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
        return handleGeneralException(e.getHttpStatus(), e);
    }

    /**
     * 정형화된 에러 응답메시지 포맷을 생성합니다.
     *
     * @param httpStatus 발생한 에러
     * @param e 익셉션 목록
     * @return ResponseEntity<ErrorResponseDTO>
     */
    private ResponseEntity<ErrorResponseDTO> handleGeneralException(HttpStatus httpStatus, Exception ...e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                                                    .errorCode(httpStatus.value())
                                                    .httpStatus(httpStatus)
                                                    .message(Arrays.stream(e)
                                                            .filter(Objects::nonNull).findFirst()
                                                            .map(Exception::getMessage)
                                                            .orElse(httpStatus.toString()))
                                                    .build();
        return new ResponseEntity<>(response, httpStatus);
    }
}
