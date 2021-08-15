package com.dj.server.test.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.test.dto.MemberResponseDTO;
import com.dj.server.test.dto.MemberTestRequestDTO;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/test")
@RestController
public class AopController {

    /**
     * aop 로그 확인을 위한 테스트 컨트롤러
     *
     * @param memberTestRequestDTO
     * @return
     */
    @GetMapping("/aop-get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<MemberResponseDTO> testAop(@RequestBody MemberTestRequestDTO memberTestRequestDTO) {
        return new ResponseDTO<>(MemberResponseDTO.builder().memberSnsId(memberTestRequestDTO.getMemberSnsId())
                .nickName(memberTestRequestDTO.getNickName()).build(), "SUCCESS", HttpStatus.OK);
    }
}
