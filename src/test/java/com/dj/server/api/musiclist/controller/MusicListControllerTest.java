package com.dj.server.api.musiclist.controller;

import com.dj.server.api.musiclist.service.MusicListService;
import com.dj.server.common.exception.musicList.handler.InvalidModifyMusicListParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MusicListControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MusicListController musicListController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(musicListController)
                .setControllerAdvice(new MusicListControllerAdvice())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("음악 재생 순서 변경 요청 테스트")
    @ExceptionHandler(InvalidModifyMusicListParameterException.class)
    public void modifyMusicListPlayOrderTest() throws Exception {
        this.mockMvc
                .perform(patch("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON +";charset=UTF-8")
                        .param("playListId", "1")
                        .param("musicIdList", "[{1}]")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}