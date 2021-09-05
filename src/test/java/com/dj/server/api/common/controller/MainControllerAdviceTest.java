package com.dj.server.api.common.controller;

import com.dj.server.api.member.controller.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class MainControllerAdviceTest {
    public MockMvc mockMvc;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new MainControllerAdvice())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("지원되지 않는 http method로 요청이 왔을 경우")
    public void httpRequestMethodNotSupported() throws Exception {
        this.mockMvc.perform(get("/v1/login/oauth2/kakao"))
                    .andDo(print())
                    .andExpect(status().isMethodNotAllowed());
    }
}