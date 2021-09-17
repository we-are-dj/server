package com.dj.server.api.playlist.controller;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("재생목록 컨트롤러 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayListControllerTest {

    final String url = "/v1/playList";

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

    private String[] playArr = {"발라드", "POP", "아이돌노래", "클래식"};

    //dummy
    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("회원의 전체 재생목록을 가져오는 테스트")
    @Test
    public void fetchMemberAllPlayListTests() throws Exception {
        //given
        final String memberName = "김철수";
        final UUID nickName = UUID.randomUUID();

        Member member = memberRepository.save(memberDummy.customNameToEntity(nickName.toString()));
        for (String playListName : playArr) {
            playListRepository.save(playListDummy.toEntityList(member, playListName));
        }

        //회원 토큰 생성.
        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));
        String accessToken = jwtUtil.createAccessToken();
        String refreshToken = jwtUtil.createRefreshToken();

        member.saveRefreshToken(refreshToken);

        //when
        // 조회 get 요청 , 인터셉터에서 토큰을 검사하기 때문에. 넣어줍니다.
        final ResultActions actions = this.mockMvc.perform(get(url)
                .header("access_token", accessToken)
                .header("refresh_token", refreshToken));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].playListName").isNotEmpty());
    }

    @DisplayName("재생목록 생성 테스트")
    @Test
    public void createPlayListTest() throws Exception {

        final String playListName = "발라드";

        final String memberName = "김영희";

        //given
        Member member = memberRepository.save(memberDummy.customNameToEntity(memberName));

        //회원 토큰 생성.
        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));
        String accessToken = jwtUtil.createAccessToken();
        String refreshToken = jwtUtil.createRefreshToken();

        member.saveRefreshToken(refreshToken);

        //when
        // 조회 get 요청 , 인터셉터에서 토큰을 검사하기 때문에. 넣어줍니다.
        final ResultActions actions = this.mockMvc.perform(post(url)
                .header("access_token", accessToken)
                .header("refresh_token", refreshToken)
                .param("playListName", playListName)); // 파라매터를 보내줍니다.

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.playListName").value(playListName))
                .andExpect(jsonPath("$.data.use").value("N"));

    }

    @DisplayName("재생목록을 업데이트 합니다.")
    @Test
    public void modifyPlayListTest() throws Exception {

        final String playListName = "임창정 노래 모음";

        final String memberName = "임창정V2";

        //given
        Member member = memberRepository.save(memberDummy.customNameToEntity(memberName));

        //회원 토큰 생성.
        jwtUtil.setTokenIngredient(String.valueOf(member.getMemberId()));
        String accessToken = jwtUtil.createAccessToken();
        String refreshToken = jwtUtil.createRefreshToken();

        member.saveRefreshToken(refreshToken);

        //노래를 하나 생성하고 반환받습니다.
        PlayList playList = playListRepository.save(playListDummy.toEntity(member));

        //when
        // 조회 get 요청 , 인터셉터에서 토큰을 검사하기 때문에. 넣어줍니다.
        final ResultActions actions = this.mockMvc.perform(patch(url)
                .header("access_token", accessToken)
                .header("refresh_token", refreshToken)
                .param("playListId", String.valueOf(playList.getPlayListId()))
                .param("modifyPlayListName", playListName)); // 파라매터를 보내줍니다 , 변경할 플레이 리스트 이름, id값


        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.playListName").value(playListName))
                .andExpect(jsonPath("$.data.playListId").value(playList.getPlayListId()));

    }


}
