package com.dj.server.exception.member;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.api.member.entity.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class MemberExceptionTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
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

        assertThat(memberRepository.save(Member.builder().memberSnsId(kakaoId).memberNickName(nickName).memberRole(MemberRole.USER).build())).hasNoNullFieldsOrPropertiesExcept();
    }
}