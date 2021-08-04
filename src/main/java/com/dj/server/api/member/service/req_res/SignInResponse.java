package com.dj.server.api.member.service.req_res;

import lombok.Getter;

/**
 *  유저의 로그인 요청 시 유효한 토큰을 담아 반환합니다.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Getter
public class SignInResponse {
    private final String token;

    public SignInResponse(String token) {
        this.token = token;
    }
}
