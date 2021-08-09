package com.dj.server.api.member.model;

import lombok.Getter;

/**
 * Member가 어떤 Oauth2 소셜인증을 통해서
 * 로그인하였는지 파악하기 위한 용도.
 *
 * @author Informix
 * @created 2021-08-07
 * @since 0.0.1
 */
@Getter
public enum SocialType {
    GOOGLE("google"),
    KAKAO("kakao");

    private final String name;

    SocialType(String name) {
        this.name = name;
    }
}