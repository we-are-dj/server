package com.dj.server.api.member.service.req_res;

import com.dj.server.api.member.entity.MemberRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저의 회원가입 요청시 필요한 회원정보들을 모아둔 클래스.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    private String email;
    private String password;
    private String nickName;
    private MemberRole memberRole;

    @Builder
    public SignUpRequest(String email, String password, String nickName, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.memberRole = memberRole;
    }
}
