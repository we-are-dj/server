package com.dj.server.api.member.service;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.api.member.service.jwt.JwtUtil;
import com.dj.server.api.member.service.req_res.SignInRequest;
import com.dj.server.api.member.service.req_res.SignInResponse;
import com.dj.server.api.member.service.req_res.SignUpRequest;
import com.dj.server.exception.member.MemberCrudErrorCode;
import com.dj.server.exception.member.MemberException;
import com.dj.server.exception.member.MemberPermitErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 유저에 대한 전반적인 비즈니스 로직을 담당합니다.
 * 대표적인 예시로 회원가입, 로그인 등을 처리합니다.
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private MemberRepository memberRepository;

        public void signUp(SignUpRequest signUpRequest) {
            verifyDuplicatedMember(signUpRequest.getEmail());

            Member newMember = Member.builder()
                    .memberEmail(signUpRequest.getEmail())
                    .memberNickName(signUpRequest.getNickName())
                    .memberPassword(signUpRequest.getPassword())
                    .memberRole(signUpRequest.getMemberRole())
                    .token(jwtUtil.createToken())
                    .build();

            memberRepository.save(newMember);
        }

        private void verifyDuplicatedMember(String memberEmail) {
            if(memberRepository.findByEmail(memberEmail).isPresent())
                throw new MemberException(MemberCrudErrorCode.DUPLICATED);
        }

        public SignInResponse signIn(SignInRequest signInRequest) {
            Member findMember = memberRepository.findByEmail(signInRequest.getEmail())
                    .orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER));

            if (!findMember.getMemberPassword().equals(signInRequest.getPassword()))
                throw new MemberException(MemberPermitErrorCode.SIGNIN_FAILED);

            return new SignInResponse(findMember.getToken());
        }
}
