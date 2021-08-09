package com.dj.server.api.member.dto.request;

import lombok.*;

/**
 * 카카오 인가코드를 사용해 액세스 토큰을 요청했을 때
 * 카카오로부터 반환되는 데이터들을 담기 위한 그릇
 *
 * @author Informix
 * @created 2021-08-07
 * @since 0.0.1
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
public class KakaoToken {

    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;

}
