package com.dj.server.api.member.service.req_res;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저의 로그인 요청시 필요한 정보를 모아둔 클래스.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {

    private String email;
    private String password;

    @Builder
    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}