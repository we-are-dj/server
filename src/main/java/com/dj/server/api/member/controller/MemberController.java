package com.dj.server.api.member.controller;

import com.dj.server.api.member.service.MemberService;
import com.dj.server.api.member.service.req_res.SignInRequest;
import com.dj.server.api.member.service.req_res.SignInResponse;
import com.dj.server.api.member.service.req_res.SignUpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 회원 도메인 관련 요청의 데이터를 보내주는 컨트롤러
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 *
 */

@Slf4j
@Api(value = "MemberController V1")
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "test", notes = "테스트!")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/api/test")
    public String test() {
        return "123";
    }

    @PostMapping("/signUp")
    public String signUp(SignUpRequest signUpRequest) {
        memberService.signUp(signUpRequest);
        return "Sign Up OK";
    }

    @PostMapping("/signIn")
    public SignInResponse signIn(SignInRequest signInRequest) {
        return memberService.signIn(signInRequest);
    }

}