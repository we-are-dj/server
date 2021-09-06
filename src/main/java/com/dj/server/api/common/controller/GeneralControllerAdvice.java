package com.dj.server.api.common.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.common.ValidParameterException;
import com.dj.server.common.exception.member.enums.MemberPermitErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

public class GeneralControllerAdvice {
    /**
     * 정형화된 에러 응답메시지 포맷을 생성합니다.
     *
     * @param httpStatus 발생한 에러
     * @param e 익셉션 목록
     * @return ResponseEntity<ErrorResponseDTO>
     */
    public static ResponseEntity<ErrorResponseDTO> handleGeneralException(HttpStatus httpStatus, Exception ...e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(Arrays.stream(e)
                        .filter(Objects::nonNull).findFirst()
                        .map(Exception::getMessage)
                        .orElse(httpStatus.toString()))
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");

        return new ResponseEntity<>(response, responseHeaders, httpStatus);
    }

    /**
     * 정형화된 에러 응답메시지 포맷을 생성합니다.
     *
     * @param httpStatus 발생한 에러
     * @param e @Valid 또는 @Validated 검증을 하는 익셉션 목록
     * @return ResponseEntity<ErrorResponseDTO>
     */
    public static ResponseEntity<ErrorResponseDTO> handleValidParamemterException(HttpStatus httpStatus, ValidParameterException ...e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(httpStatus.value())
                .httpStatus(httpStatus)
                .message(Arrays.stream(e)
                        .filter(Objects::nonNull).findFirst()
                        .map(Exception::getMessage)
                        .orElse(httpStatus.toString()))
                .errors(Arrays.stream(e)
                              .filter(Objects::nonNull)
                              .findFirst()
                              .orElseThrow(() -> new BizException(MemberPermitErrorCode.INVALID_MEMBER))
                              .getErrors())
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8");

        return new ResponseEntity<>(response, responseHeaders, httpStatus);
    }
}
