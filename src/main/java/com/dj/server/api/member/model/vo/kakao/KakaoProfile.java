package com.dj.server.api.member.model.vo.kakao;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.enums.MemberRole;
import com.dj.server.api.member.entity.enums.SocialType;
import com.dj.server.api.member.entity.enums.StatusType;
import lombok.*;

/**
 * 카카오 액세스토큰을 사용하여 카카오에 유저의 정보를 요청할 떄
 * 반환되는 유저 정보를 담을 그릇으로 사용됩니다.
 *
 * @author Informix
 * @author JaeHyun
 * @created 2021-08-07
 * @since 0.0.1
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class KakaoProfile {

    private Long id;
    private KakaoAccount kakao_account;

    /**
     * @see com.dj.server.api.member.service.MemberService
     * @return 신규 로그인 유저 정보
     */
    public Member toEntity() {
        String time = String.valueOf(System.currentTimeMillis()).substring(8);
        return Member.builder()
                .memberSnsId(String.valueOf(id))
                .memberNickName(kakao_account.getProfile().getNickname() + "_" + time)
                .memberName(kakao_account.getProfile().getNickname())
                .memberSts(StatusType.NORMAL)
                .memberRole(MemberRole.USER)
                .socialType(SocialType.KAKAO)
                .build();
    }

}
