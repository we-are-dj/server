package com.dj.server.test.controller;

import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import lombok.ToString;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class ExceptionController {
    @GetMapping("permit-exception")
    public String sendAuthorityError() {
        throw new MemberException(MemberPermitErrorCode.INVALID_MEMBER);
    }

    @GetMapping("crud-exception")
    public String sendCrudError() {
        throw new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER);
    }

}