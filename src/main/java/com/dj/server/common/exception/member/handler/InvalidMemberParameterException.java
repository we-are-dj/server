package com.dj.server.common.exception.member.handler;

import com.dj.server.common.exception.common.ValidParameterException;
import lombok.Getter;
import org.springframework.validation.Errors;

public class InvalidMemberParameterException extends ValidParameterException {

    public InvalidMemberParameterException(Errors errors) {
        super(errors);
    }

}
