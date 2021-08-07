package com.dj.server.api.common.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.KakaoRequest;
import com.dj.server.api.member.dto.request.KakaoProfile;
import com.dj.server.api.member.dto.request.KakaoToken;
import com.dj.server.api.member.dto.request.MemberTestRequestDTO;
import com.dj.server.api.member.dto.response.MemberResponseDTO;
import com.dj.server.api.member.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.service.MemberService;
import com.dj.server.exception.member.MemberCrudErrorCode;
import com.dj.server.exception.member.MemberException;
import com.dj.server.exception.member.MemberPermitErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 각종 테스트를 위한 테스트 컨트롤러
 *
 * @author Informix, JaeHyun
 * @created 2021-08-03
 * @update 2021-08-06
 * @since 0.0.1
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final MemberService memberService;
    private final KakaoRequest kakaoRequest;

    @PostMapping(value = {"/test-cors/post"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPost() {
        return "cors post passed!";
    }

    @GetMapping(value = {"/test-cors/get"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsGet() {
        return "cors get passed!";
    }

    @PutMapping(value = {"/test-cors/put"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPut() {
        return "cors put passed!";
    }

    @DeleteMapping(value = {"/test-cors/delete"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsDelete() {
        return "cors delete passed!";
    }

    @GetMapping("/test-response")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO<MemberResponseDTO>> testResponseDTO() {
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.builder()
                .memberSnsId("kakao123")
                .nickName("홍길동").build();
        ResponseDTO<MemberResponseDTO> responseDTOResponseDTO = new ResponseDTO<>(memberResponseDTO, "Ok");
        return new ResponseEntity<>(responseDTOResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/test-permit-exception")
    public String sendAuthorityError() {
        throw new MemberException(MemberPermitErrorCode.INVALID_MEMBER);
    }

    @GetMapping("/test-crud-exception")
    public String sendCrudError() {
        throw new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER);
    }


    /**
     * aop 로그 확인을 위한 테스트 컨트롤러
     *
     * @param memberTestRequestDTO
     * @return
     */
    @GetMapping("/test-aop/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO<MemberResponseDTO>> testAop(@RequestBody MemberTestRequestDTO memberTestRequestDTO) {
        return new ResponseEntity<>(new ResponseDTO<>(MemberResponseDTO.builder().memberSnsId(memberTestRequestDTO.getMemberSnsId())
                .nickName(memberTestRequestDTO.getNickName()).build(), "SUCCESS"), HttpStatus.OK);
    }


    @PostMapping("/login/oauth2/kakao")
    public ResponseDTO<ResponseTokenDTO> getKakaoAuthCodeAndsendToken(@RequestParam("code") String code, @RequestParam("redirect_url") String url) {
        KakaoToken kakaoToken = kakaoRequest.getAccessToken(code, url);
        KakaoProfile kakaoProfile = kakaoRequest.getKakaoProfile(kakaoToken);

        // jwt
        // 우리 서버가 생성한 jwt 토큰 두개를 같이 멤버에 넣어서 반환
        return new ResponseDTO<>(memberService.getToken(kakaoProfile), "SUCCESS");
    }

}
