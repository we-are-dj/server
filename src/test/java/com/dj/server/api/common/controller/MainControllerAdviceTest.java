package com.dj.server.api.common.controller;

import com.dj.server.api.member.controller.MemberController;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("존재하지 않는 url로 요청이 올 경우")
    public void noURLexisted() throws Exception {
        this.mockMvc.perform(get("/DOES_NOT_EXIST_URL"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("url에 특수문자가 포함된 경우")
    public void nonASCIIParamInURL() throws Exception {
        this.mockMvc.perform(get("/[]\\||/[]?data=[||]\\"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("지원되지 않는 http method로 요청이 왔을 경우")
    public void httpMethodNotSupported() throws Exception {
        mockMvc.perform(get("/v1/logout"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(result -> assertThat(getApiResultExceptionClass(result)).isAssignableFrom(HttpRequestMethodNotSupportedException.class));
    }

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException()).getClass();
    }

}