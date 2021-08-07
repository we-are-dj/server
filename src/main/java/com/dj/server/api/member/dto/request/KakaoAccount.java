package com.dj.server.api.member.dto.request;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class KakaoAccount {

    private Profile profile;

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Profile {
        private String nickname;
    }
}
