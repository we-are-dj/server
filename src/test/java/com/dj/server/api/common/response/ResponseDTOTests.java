package com.dj.server.api.common.response;


import com.dj.server.test.controller.KakaoOauth2Controller;
import com.dj.server.test.controller.ResponseController;
import com.dj.server.api.member.dto.request.KakaoRequest;
import com.dj.server.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ResponseController.class)
public class ResponseDTOTests {

    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private KakaoRequest kakaoRequest;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new KakaoOauth2Controller(memberService, kakaoRequest)).addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
    }

    @Test
    @DisplayName("Common Response DTO Test")
    public void commonResponseDTOTest() throws Exception {
        this.mockMvc.perform(get("/test-response"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberSnsId").value("kakao123"))
                .andExpect(jsonPath("$.data.nickName").value("홍길동"));
    }

}
