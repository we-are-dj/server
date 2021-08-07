package com.dj.server.api.member;

import com.dj.server.api.member.dto.request.KakaoProfile;
import com.dj.server.api.member.dto.request.KakaoToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Getter
@Component
public class KakaoRequest {

    @Value("${authorization-grant-type}")
    private String grantType;

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value("${redirect-url}")
    private String redirectUri;

    public KakaoToken getAccessToken(String code, String url) {

        HttpHeaders header = new HttpHeaders();

        header.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", this.grantType);
        body.add("client_id", this.clientId);
        body.add("redirect_uri", url);
        body.add("code", code);
//        body.add("client_secret", this.clientSecret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, header);
        RestTemplate rt = new RestTemplate();

        System.out.println(kakaoTokenRequest);

        return rt.postForObject(
                "https://kauth.kakao.com/oauth/token",
                kakaoTokenRequest,
                KakaoToken.class
        );
    }

    public KakaoProfile getKakaoProfile(KakaoToken kakaoToken) {

        final String authorizationValue = "Bearer " + kakaoToken.getAccess_token();

        HttpHeaders header = new HttpHeaders();


        header.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        header.add("Authorization", authorizationValue);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //body.add("client_secret", this.clientSecret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, header);
        RestTemplate restTemplate = new RestTemplate();




        return restTemplate.postForObject(
                "https://kapi.kakao.com/v2/user/me",
                kakaoTokenRequest,
                KakaoProfile.class);
    }

}
