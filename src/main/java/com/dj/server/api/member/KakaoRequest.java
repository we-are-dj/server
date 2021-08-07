package com.dj.server.api.member;

import com.dj.server.api.member.dto.request.KakaoProfile;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Getter
public class KakaoRequest {

    @Value("${spring.security.oauth2.client.registration.kakao.authorization.grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization.redirect-uri}")
    private String redirectUri;

    public String getAccessToken(String code) {

        HttpHeaders header = new HttpHeaders();

        header.add("Content-type", "application-x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", getGrantType());
        body.add("client_id", getClientId());
        body.add("redirect_uri", getRedirectUri());
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, header);
        RestTemplate rt = new RestTemplate();

        return rt.postForObject(
                "https://kauth.kakao.com/oauth/token",
                kakaoTokenRequest,
                String.class
        );
    }

    public KakaoProfile getKakaoProfile(String accessToken) {

        final String authorizationValue = "Bearer " + accessToken;

        HttpHeaders header = new HttpHeaders();


        header.add("Content-type", "application-x-www-form-urlencoded;charset=utf-8");
        header.add("Authorization", authorizationValue);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(header);
        RestTemplate rt = new RestTemplate();

        return rt.postForObject(
                "https://kauth.kakao.com/oauth/token",
                kakaoTokenRequest,
                KakaoProfile.class
        );


    }

}
