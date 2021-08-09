package com.dj.server.api.member.entity;

import lombok.Getter;

/**
 * Member가 어떤 Oauth2 소셜인증을 통해
 * 서비스를 이용하기 시작했는지 파악하기 위해 만들어짐.
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