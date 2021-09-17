package com.dj.server.api.member.controller;

import com.dj.server.common.exception.member.handler.InvalidMemberParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new MemberControllerAdvice())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("로그인 요청시 헤더에 인가코드(code)가 없는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerDoesNotHaveKakaoAuthCode() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    @DisplayName("로그인 요청시 헤더에 code 값이 비어있는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerHaveEmptyKakaoAuthCode() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("code", "")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    @DisplayName("로그인 요청시 헤더에 redirectUri 값이 없는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerDoesNotHaveRedirectUri() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("code", "카카오인가코드")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName("로그인 요청시 헤더에 redirectUri 값이 비어있는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerHaveEmptyRedirectUri() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("code", "카카오인가코드")
                        .header("redirectUri", "")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    @DisplayName("지원되지 않는 http method로 요청이 왔을 경우")
    public void httpRequestMethodNotSupported() throws Exception {
        this.mockMvc.perform(get("/v1/login/oauth2/kakao"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
}
