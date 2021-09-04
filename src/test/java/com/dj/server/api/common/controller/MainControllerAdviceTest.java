package com.dj.server.api.common.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class MainControllerAdviceTest {
    public MockMvc mockMvc;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController)
                .setControllerAdvice(new MainControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("존재하지 않는 url로 요청이 올 경우")
    public void noURLexisted() throws Exception {
        this.mockMvc.perform(get("/DOES_NOT_EXIST_URL"))
                                            .andDo(print())
                                            .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(getApiResultExceptionClass(result)).isAssignableFrom(NoHandlerFoundException.class));
    }

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException()).getClass();
    }
}