package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.musicList.handler.InvalidModifyMusicListParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.dj.server.api.common.controller.GeneralControllerAdvice.handleValidParamemterException;

public class MusicListControllerAdvice {

    /**
     * # @Valid 또는 @Validated 애너테이션을 통한 검증 실패시 동작합니다.
     * # @Valid 또는 @Validated 애너테이션을 사용하는 컨트롤러 메서드에 대한 예외 응답 처리시
     * errors 인자값을 설정하면, 보다 자세한 에러메시지를 클라이언트에 전달할 수 있습니다.
     *
     * @param e InvalidModifyMusicListParameterException
     * @return 400 (Bad Request: invalid parameter error)
     * @see ErrorResponseDTO
     */
    @ExceptionHandler(InvalidModifyMusicListParameterException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidModifyMusicListParameterException(InvalidModifyMusicListParameterException e) {
        return handleValidParamemterException(HttpStatus.BAD_REQUEST, e);
    }
}
