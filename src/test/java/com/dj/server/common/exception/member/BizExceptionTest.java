package com.dj.server.common.exception.member;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.enums.SocialType;
import com.dj.server.api.member.entity.enums.StatusType;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.member.entity.enums.MemberRole;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.exception.common.BizException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BizExceptionTest {

    @Autowired
    private MemberRepository memberRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();

    @Test
    @Order(1)
    @DisplayName("회원의 데이터를 생성합니다. 첫번째로 실행됩니다.")
    @Transactional(readOnly = true)
    @ExceptionHandler()
    public void createMemberEntity() throws BizException {

        String kakaoId = "kakaoId123";
        String nickName = "홍길동";

        //given
        Long memberId = memberRepository.save(memberDummy.toEntity()).getMemberId();

        //when
        //Member member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("회원 아이디 생성 실패"));

        //then

        /*
        try {
            memberRepository.save(Member.builder().memberSnsId(kakaoId).memberNickName(nickName).memberRole(MemberRole.USER).build());
        } catch (DataIntegrityViolationException e) {
            throw new MemberException(MemberErrorCode.DUPLICATED);
        }*/

        Exception e = assertThrows(DataIntegrityViolationException.class,
                                            () -> memberRepository.save(Member.builder()
                                                                        .memberSnsId(kakaoId)
                                                                        .memberNickName(nickName)
                                                                        .memberRole(MemberRole.USER)
                                                                        .memberSts(StatusType.NORMAL)
                                                                        .socialType(SocialType.KAKAO)
                                                                        .build()));
        assertTrue(e.getCause() instanceof PersistenceException);


    }
}