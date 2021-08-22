package com.dj.server.api.musiclist.controller;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.musiclist.dto.request.MusicListSaveRequestDTO;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import com.dj.server.common.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("뮤직리스트 컨트롤러 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MusicControllerTest {

    final String url = "/v1/musicList";

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository playListRepository;

    private MockMvc mockMvc;

    //dummy
    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }

    @DisplayName("뮤직리스트 생성하는 컨트롤러 테스트")
    @Test
    public void saveMusicListTests() throws Exception {

        //given
        final String playListName = "발라드";
        final String musicUrl = "watch?v=6RQ-bBdASvk";
        final String thumbnail = "thumbnail123";
        final String playtime = "22:08";
        final UUID nickName = UUID.randomUUID();

        Member member = memberRepository.save(memberDummy.customNameToEntity(nickName.toString()));

        PlayList playList = playListRepository.save(playListDummy.toEntityList(member, playListName));

        MusicListSaveRequestDTO musicListSaveRequestDTO = MusicListSaveRequestDTO.builder().playListId(playList.getPlayListId()).musicUrl(musicUrl).thumbnail(thumbnail).playtime(playtime).build();

        //회원 토큰 생성.
        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));
        String accessToken = jwtUtil.createAccessToken();
        String refreshToken = jwtUtil.createRefreshToken();

        final ResultActions actions = this.mockMvc.perform(post(url)
                .header("access_token", accessToken)
                .header("refresh_token", refreshToken)
                .param("playListId", String.valueOf(musicListSaveRequestDTO.getPlayListId()))
                .param("musicUrl", musicListSaveRequestDTO.getMusicUrl())
                .param("thumbnail", musicListSaveRequestDTO.getThumbnail())
                .param("playtime", musicListSaveRequestDTO.getPlaytime()));


        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.musicUrl").value(musicListSaveRequestDTO.getMusicUrl()))
                .andExpect(jsonPath("$.data.thumbnail").value(musicListSaveRequestDTO.getThumbnail()))
                .andExpect(jsonPath("$.data.playtime").value(musicListSaveRequestDTO.getPlaytime()));



    }

}
