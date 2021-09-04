package com.dj.server.common.exception.member;


import com.dj.server.common.exception.common.BizException;
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
