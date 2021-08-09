package com.dj.server.api.member.dto.request;

import lombok.*;

/**
 * 카카오 액세스토큰을 사용하여 카카오에 유저의 정보를 요청할 때
 * 카카오계정정보는 json 타입의 kakao_account에 담긴 json 타입의 profile에 실려있으므로,
 * 이 profile 정보를 담기 위해 사용하는 클래스.
 *
 * 카카오 서버는 profile를 항상 kakao_account 안에 실려서 반환하기 때문에
 * profile를 중첩 클래스로 사용합니다.
 *
 * @author Informix
 * @created 2021-08-07
 * @since 0.0.1
 */
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
