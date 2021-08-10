package com.dj.server.api.member.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.service.oauth2.kakao.vo.KakaoProfile;
import com.dj.server.api.member.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 회원 도메인 관련 요청의 데이터를 보내주는 컨트롤러
 *
 * @author JaeHyun
 * @author Informix
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

    private final MemberService memberService;

    @ApiOperation(value = "test", notes = "테스트!")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/api/test")
    public String test() {
        return "123";
    }


    @ApiOperation(value = "signUp", notes = "회원가입 및 로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/login/oauth2/kakao")
    public ResponseDTO<ResponseTokenDTO> signUp(@RequestParam("code") String code, @RequestParam("redirect_uri") String uri) {
        KakaoProfile kakaoProfile = memberService.getKakaoProfile(code, uri);
        return new ResponseDTO<>(memberService.getGeneratedTokens(kakaoProfile), "SUCCESS", HttpStatus.OK);
    }

}