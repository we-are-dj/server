package com.dj.server.api.common.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.member.dto.MemberResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CORS를 테스트하기 위하여 만들어진 컨트롤러
 *
 * @author Informix
 * @created 2021-08-03
 * @since 0.0.1
 */
@RestController
public class TestController {

    @PostMapping(value = {"/test-cors/post"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPost() {
        return "cors post passed!";
    }

    @GetMapping(value = {"/test-cors/get"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsGet() {
        return "cors get passed!";
    }

    @PutMapping(value = {"/test-cors/put"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPut() {
        return "cors put passed!";
    }

    @DeleteMapping(value = {"/test-cors/delete"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsDelete() {
        return "cors delete passed!";
    }

    @GetMapping("/test-response")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO<MemberResponseDTO>> testResponseDTO() {
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.builder()
                .memberSnsId("kakao123")
                .nickName("홍길동").build();
        ResponseDTO<MemberResponseDTO> responseDTOResponseDTO= new ResponseDTO<>(memberResponseDTO,"Ok");
        return new ResponseEntity<>(responseDTOResponseDTO, HttpStatus.OK);
    }

}
