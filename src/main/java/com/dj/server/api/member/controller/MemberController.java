package com.dj.server.api.member.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.model.dto.request.MemberSaveRequestDTO;
import com.dj.server.api.member.model.vo.kakao.KakaoProfile;
import com.dj.server.api.member.model.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.service.MemberService;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    @PostMapping("/login/oauth2/kakao")
    public ResponseDTO<ResponseTokenDTO> signUp(MemberSaveRequestDTO memberSaveRequestDTO) {
        KakaoProfile kakaoProfile = memberService.getKakaoProfile(memberSaveRequestDTO);
        return new ResponseDTO<>(memberService.getGeneratedTokens(kakaoProfile), "SUCCESS", HttpStatus.OK);
    }
    
    @ApiOperation(value = "logout", notes = "로그아웃")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "회원이 존재하지 않습니다")
    })
    @DeleteMapping("/logout")
    public ResponseDTO<String> signOut() {
        memberService.invalidateRefreshToken();
        return new ResponseDTO<>("로그아웃되었습니다.", "SUCCESS", HttpStatus.OK);
    }

}
