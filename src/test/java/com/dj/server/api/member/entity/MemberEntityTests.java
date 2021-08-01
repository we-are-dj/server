package com.dj.server.api.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("회원 Entity 를 테스트 하는 클래스 입니다.")
public class MemberEntityTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Order(1)
    @DisplayName("회원의 데이터를 생성합니다. 첫번째로 실행됩니다.")
    public void createMemberEntity() {

        String kakaoId = "kakaoId123";
        String nickName = "홍길동";

        //given
        Long memberId = memberRepository.save(Member.builder().memberSnsId(kakaoId).memberNickName(nickName).memberRole(MemberRole.USER).build()).getMemberId();

        //when
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("회원 아이디 생성 실패"));

        //then
        assertThat(member.getMemberId()).isEqualTo(memberId);
        assertThat(member.getMemberSnsId()).isEqualTo(kakaoId);
        assertThat(member.getMemberNickName()).isEqualTo(nickName);

    }

}
