package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.controller.MainControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class SearchYoutubeTest {

    private static MockMvc mockMvc;

    @Autowired
    private MusicListController musicListController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(musicListController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new MusicListControllerAdvice(), new MainControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("유튜브 검색 테스트")
    public void searchYTMusicTest() throws Exception {
        mockMvc.perform(get("/v1/youtube")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("keyword", "아이유")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
