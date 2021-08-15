package com.dj.server.api.member.entity;


import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("QueryDsl 을 테스트 합니다")
@DataJpaTest
public class MemberQueryDSLTests {

    @Autowired
    private MemberRepository memberRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();


    @Test
    @Order(1)
    @DisplayName("QueryDsl 테스트")
    public void queryDSLTest() {

        // given
        final String nickName = "홍길동";
        Member memberId = memberRepository.save(memberDummy.toEntity());

        // when -> QueryDsl
        Member member = memberRepository.findByMemberNickName(nickName);


        assertThat(member.getMemberNickName()).isEqualTo(nickName);

    }

}
