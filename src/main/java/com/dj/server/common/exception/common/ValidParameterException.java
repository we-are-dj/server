package com.dj.server.common.exception.common;

import com.dj.server.common.exception.member.enums.MemberPermitErrorCode;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public abstract class ValidParameterException extends BizException {

    private final Errors errors;

    public ValidParameterException(Errors errors) {
        super(MemberPermitErrorCode.INVALID_MEMBER);
        this.errors = errors;
    }
}
