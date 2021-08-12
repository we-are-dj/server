package com.dj.server.test.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.model.dto.response.MemberResponseDTO;
import com.dj.server.test.dto.MemberTestRequestDTO;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ToString
@RestController
public class AopController {

    /**
     * aop 로그 확인을 위한 테스트 컨트롤러
     *
     * @param memberTestRequestDTO
     * @return
     */
    @GetMapping("/test-aop/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<MemberResponseDTO> testAop(@RequestBody MemberTestRequestDTO memberTestRequestDTO) {
        return new ResponseDTO<>(MemberResponseDTO.builder().memberSnsId(memberTestRequestDTO.getMemberSnsId())
                .nickName(memberTestRequestDTO.getNickName()).build(), "SUCCESS", HttpStatus.OK);
    }
}
