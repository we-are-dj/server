package com.dj.server.common.exception.musicList.handler;

import com.dj.server.common.exception.common.ValidParameterException;
import org.springframework.validation.Errors;

public class InvalidMusicListParameterException extends ValidParameterException {

    public InvalidMusicListParameterException(Errors errors) {
        super(errors);
    }

}
