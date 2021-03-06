package com.dj.server.api.member.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.model.dto.request.MemberSaveRequestDTO;
import com.dj.server.api.member.model.vo.kakao.KakaoProfile;
import com.dj.server.api.member.model.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.service.MemberService;
import com.dj.server.common.exception.member.handler.InvalidMemberParameterException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "signUp", notes = "회원가입 및 로그인")
    @ApiResponses({ // 리스폰스 코드 설명
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({ // 파라미터 설명
    })
    @PostMapping("/login/oauth2/kakao")
    public ResponseDTO<ResponseTokenDTO> signUp(@Valid MemberSaveRequestDTO memberSaveRequestDTO, BindingResult result) {
        if (result.hasErrors()) throw new InvalidMemberParameterException(result);

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

    @ApiOperation(value = "updateNickName", notes = "닉네임 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "회원이 존재하지 않습니다")
    })
    @PatchMapping("/members/nickname")
    public ResponseDTO<Map<String, String>> updateNickName(@RequestParam("nickname") String nickName) {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", memberService.updateNickName(nickName));
        return new ResponseDTO<>(map, "SUCCESS", HttpStatus.OK);
    }

}
