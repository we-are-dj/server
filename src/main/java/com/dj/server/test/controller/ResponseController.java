package com.dj.server.test.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.test.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 각종 테스트를 위한 테스트 컨트롤러
 *
 * @author Informix, JaeHyun
 * @created 2021-08-03
 * @update 2021-08-06
 * @since 0.0.1
 */

@Slf4j
@RequestMapping("/test")
@RestController
public class ResponseController {

    @GetMapping("/response")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO<MemberResponseDTO>> testResponseDTO() {
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.builder()
                .memberSnsId("kakao123")
                .nickName("홍길동").build();
        ResponseDTO<MemberResponseDTO> responseDTOResponseDTO = new ResponseDTO<>(memberResponseDTO, "Ok", HttpStatus.OK);
        return new ResponseEntity<>(responseDTOResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/simple")
    public String test() {
        return "123";
    }
}
