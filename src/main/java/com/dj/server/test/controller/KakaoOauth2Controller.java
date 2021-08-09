package com.dj.server.test.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.dto.request.KakaoProfile;
import com.dj.server.api.member.dto.request.KakaoRequest;
import com.dj.server.api.member.dto.request.KakaoToken;
import com.dj.server.api.member.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class KakaoOauth2Controller {
    private final MemberService memberService;
    private final KakaoRequest kakaoRequest;

    @PostMapping("/login/oauth2/kakao")
    public ResponseDTO<ResponseTokenDTO> getKakaoAuthCodeAndsendToken(@RequestParam("code") String code, @RequestParam("redirect_url") String url) {
        KakaoToken kakaoToken = kakaoRequest.getKakaoAccessToken(code, url);
        KakaoProfile kakaoProfile = kakaoRequest.getKakaoProfile(kakaoToken);

        // jwt
        // 우리 서버가 생성한 jwt 토큰 두개를 같이 멤버에 넣어서 반환
        return new ResponseDTO<>(memberService.getToken(kakaoProfile), "SUCCESS");
    }
}
