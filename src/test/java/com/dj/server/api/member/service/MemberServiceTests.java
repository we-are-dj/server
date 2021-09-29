package com.dj.server.api.member.service;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.jwt.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //단일 실행 다른 테스트코드에 영향이 안감
public class MemberServiceTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberService memberService;

    MemberDummy memberDummy = MemberDummy.getInstance();

    @Test
    @DisplayName("회원 닉네임 변경 테스트.")
    public void modifyMemberNickNameTests() {

        final String changeNickName = "변경";


        Member member = memberRepository.save(memberDummy.toEntity());

        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));


        String nickName =  memberService.updateNickName(changeNickName);

        assertThat(changeNickName).isEqualTo(nickName);

    }

    @Test
    @DisplayName("회원 닉네임 중복 처리 테스트")
    public void modifyMemberNickNameErrorTests() {
        memberRepository.save(memberDummy.toEntity());
        Member member = memberRepository.save(memberDummy.customNameToEntity("김철수"));

        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));


        assertThrows(BizException.class, () -> memberService.updateNickName(memberDummy.getMemberNickName()));

    }

}
