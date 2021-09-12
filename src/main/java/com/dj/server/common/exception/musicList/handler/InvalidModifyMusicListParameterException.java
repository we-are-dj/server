package com.dj.server.common.exception.musicList.handler;

import com.dj.server.common.exception.common.ValidParameterException;
import org.springframework.validation.Errors;

public class InvalidModifyMusicListParameterException extends ValidParameterException {

    public InvalidModifyMusicListParameterException(Errors errors) {
        super(errors);
    }

}
