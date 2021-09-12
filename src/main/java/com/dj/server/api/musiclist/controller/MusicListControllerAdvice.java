package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.musicList.handler.InvalidModifyMusicListParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.dj.server.api.common.controller.GeneralControllerAdvice.handleValidParamemterException;

@RestControllerAdvice
public class MusicListControllerAdvice {

    @ExceptionHandler(InvalidModifyMusicListParameterException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidModifyMusicListParameterException(InvalidModifyMusicListParameterException e) {
        return handleValidParamemterException(HttpStatus.BAD_REQUEST, e);
    }
}
