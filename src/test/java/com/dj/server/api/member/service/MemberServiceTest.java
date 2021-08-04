package com.dj.server.api.member.service;

import com.dj.server.api.member.controller.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberServiceTest {

    private MockMvc mockMvc;
    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MemberController()).addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
        this.mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/signIn").header("memberEmail", "find1086@gmail.com");
    }

    @Test
    @DisplayName("로그인 테스트")
    public void signInTest() throws Exception {
        this.mockMvc.perform(this.mockHttpServletRequestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}