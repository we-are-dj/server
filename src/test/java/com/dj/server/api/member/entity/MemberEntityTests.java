package com.dj.server.api.member.entity;

import com.dj.server.api.member.entity.enums.StatusType;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("회원 Entity 를 테스트 하는 클래스 입니다.")
@DataJpaTest
public class MemberEntityTests {

    @Autowired
    private MemberRepository memberRepository;

    //더미 클래스
    private final MemberDummy memberDummy = MemberDummy.getInstance();

    @BeforeEach
    public void setUp() {
        //given
        Long memberId = memberRepository.save(memberDummy.toEntity()).getMemberId();

        //when
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("회원 아이디 생성 실패"));

        //then
        assertThat(member.getMemberId()).isEqualTo(memberId);
        assertThat(member.getMemberSnsId()).isEqualTo(memberDummy.getMemberSnsId());
        assertThat(member.getMemberNickName()).isEqualTo(memberDummy.getMemberNickName());
    }


    @Test
    @Order(1)
    @DisplayName("회원의 닉네임을 변경합니다.")
    public void updateMemberNickName() {

        String changeNickName = "변경할거임!";

        //given
        Member member = memberRepository.findAll().get(0);
        String notUpdateNickName = member.getMemberNickName();

        //when
        member.updateNickName(changeNickName);

        Member updateMember = memberRepository.findById(member.getMemberId()).orElseThrow(() -> new NullPointerException("회원이 존재 하지 않습니다."));

        assertThat(updateMember.getMemberNickName()).isEqualTo(changeNickName);
        assertThat(updateMember.getMemberNickName()).isNotEqualTo(notUpdateNickName); // 변경전 닉네임

    }

    @Test
    @Order(2)
    @DisplayName("회원 테이블 데이터 삭제 테스트 입니다.")
    public void deleteMember() {

        //given
        Member member = memberRepository.findAll().get(0);
        Long memberId = member.getMemberId();

        //then
        memberRepository.delete(member);

        //then
        assertThrows(NoSuchElementException.class, () -> memberRepository.findById(memberId).get());
    }

    @Test
    @Order(3)
    @DisplayName("회원 상태값 테스트")
    public void memberStsTest() {

        Member member = memberRepository.findAll().get(0); //

        assertThat(member.getMemberSts()).isEqualTo(StatusType.NORMAL);

    }

    @Test
    @Order(4)
    @DisplayName("Date 가 잘 들어갔는지 테스트 합니다.")
    public void confirmMemberCreateDate() {

        Member member = memberRepository.findAll().get(0);
        assertThat(member.getCreateAt()).isNotNull();
    }

}
