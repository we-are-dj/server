package com.dj.server.api.member.service.oauth2.kakao.request;


import com.dj.server.api.member.model.vo.kakao.KakaoProfile;
import com.dj.server.api.member.model.vo.kakao.KakaoToken;
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

/**
 * 프론트엔드 서버로부터 받은 카카오 로그인을 한 유저의 인가코드를 사용하여
 * 액세스 토큰 발급을 카카오에 요청하고,
 * 반환된 액세스 토큰을 사용하여
 * 다시금 유저 정보를 카카오에 요청하는 목적으로 사용되는 클래스.
 *
 * @author Informix
 * @author JaeHyun
 * @created 2021-08-07
 * @since 0.0.1
 */
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

    @Value("${redirect-uri}")
    private String redirectUri;

    /**
     * 1. 사용자가 카카오로 로그인을 시도할 시 프론트엔드 서버는 카카오서버로부터 인가코드를 받게 됩니다.
     * 2. 프론트엔트 서버는 인가코드 및 자신의 서버 주소를 백엔드 서버로 전송합니다.
     * 3. getKakaoAccessToken에서 인가코드를 사용하여 카카오로 액세스토큰을 요청합니다.
     * 4. 카카오에 액세스토큰을 요청할 때 액세스토큰을 포함한 다른 정보들이 함께 포함되어 반환되므로
     *    이 정보들을 담기 위한 KakaoToken가 필요합니다.
     *
     * @param code 카카오 인가코드
     * @param uri 카카오로부터 인가코드를 받은 서버의 주소. 즉 redirect uri.
     *
     * @return 카카오[토큰타입, 액세스토큰, 액세스토큰 만료시간, 리프레시토큰, 리프레시토큰 만료시간]
     * @see KakaoToken
     * @since 0.0.1
     */
    public KakaoToken getKakaoAccessToken(String code, String uri) {

        HttpHeaders header = new HttpHeaders();

        header.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", this.grantType);
        body.add("client_id", this.clientId);
        body.add("redirect_uri", uri);
        body.add("code", code);
        // body.add("client_secret", this.clientSecret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, header);
        RestTemplate rt = new RestTemplate();

        return rt.postForObject(
                "https://kauth.kakao.com/oauth/token",
                kakaoTokenRequest,
                KakaoToken.class
        );
    }

    /**
     * 카카오 액세스토큰을 사용하여 로그인을 요청한 유저의 정보를 서버로 가져옵니다.
     *
     * @param kakaoToken 카카오로부터 받은 액세스토큰
     * @return 카카오 유저정보를 담은 KakaoProfile instance
     * @since 0.0.1
     */
    public KakaoProfile getKakaoProfile(KakaoToken kakaoToken) {

        final String authorization = "Bearer " + kakaoToken.getAccess_token();

        HttpHeaders header = new HttpHeaders();

        header.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        header.add("Authorization", authorization);

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
