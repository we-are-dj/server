package com.dj.server.api.member.entity;

import lombok.Getter;

@Getter
public enum SocialType {
    GOOGLE("google"),
    KAKAO("kakao");

    private final String name;

    SocialType(String name) {
        this.name = name;
    }
}