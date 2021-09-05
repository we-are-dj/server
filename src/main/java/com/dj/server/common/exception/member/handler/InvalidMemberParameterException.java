package com.dj.server.common.exception.member.handler;

import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.member.enums.MemberPermitErrorCode;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class InvalidMemberParameterException extends BizException {

    private final Errors errors;

    public InvalidMemberParameterException(Errors errors) {
        super(MemberPermitErrorCode.INVALID_MEMBER);
        this.errors = errors;
    }
}
