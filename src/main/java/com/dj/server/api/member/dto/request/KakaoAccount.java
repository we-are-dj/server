package com.dj.server.api.member.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class KakaoAccount {

    private final Profile profile;

    @Getter
    @RequiredArgsConstructor
    public static class Profile {
        private final String nickname;
    }
}
