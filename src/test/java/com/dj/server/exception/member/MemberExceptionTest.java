package com.dj.server.exception.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class MemberExceptionTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test(ex)
    @Order(1)
    @DisplayName("회원의 데이터를 생성합니다. 첫번째로 실행됩니다.")
    @Transactional(readOnly = true)
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
        System.out.println("member.getMemberId()" + member.getMemberId());
    }

    @Test
    @Order(2)
    @DisplayName("회원의 닉네임을 변경합니다.")
    @Transactional
    public void updateMemberNickName()


}