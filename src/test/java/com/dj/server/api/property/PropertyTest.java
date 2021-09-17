package com.dj.server.api.property;

import com.dj.server.api.common.controller.MainControllerAdvice;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.musiclist.controller.MusicListController;
import com.dj.server.api.musiclist.repository.MusicListRepository;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.api.properties.entity.Property;
import com.dj.server.api.properties.repository.PropertyRepository;
import com.dj.server.api.properties.service.PropertyService;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.musiclist.MusicListDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import com.dj.server.common.interceptor.JwtAuthInterceptor;
import com.dj.server.common.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropertyTest {

    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private MusicListRepository musicListRepository;

    @InjectMocks
    private MusicListController musicListController;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PropertyService propertyService;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();
    private final MusicListDummy musicListDummy = MusicListDummy.getInstance();

    @BeforeEach
    public void setup() {
        Member member = memberRepository.save(memberDummy.toEntity());
        PlayList playList = playListRepository.save(playListDummy.toEntity(member));
        musicListRepository.save(musicListDummy.toEntity(playList, 1, "MusicURL__TEST_31"));
        propertyRepository.save(new Property("pk1", "pwd123", "prop 설명"));

        mockMvc = MockMvcBuilders.standaloneSetup(musicListController)
                .addInterceptors(new JwtAuthInterceptor(jwtUtil, propertyService))
                .setControllerAdvice(new MainControllerAdvice(), new MainControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("헤더에 prop val도 없고 memberId도 없는 경우")
    public void headerDoesNotHavePropValAndMemberId() throws Exception {
        this.mockMvc.perform(post("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.header("prop_value", "pwd123")
                        //.header("memberId", "30")
                        .content("{\"playListId\":\"1\", \"musicUrl\":\"Dt-WNXvN2Zs\", \"thumbnail\":\"/rain.png\", \"playtime\":\"02:40\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("헤더에 memberId만 있고 prop_value는 없는 경우")
    public void headerDoesNotHavePropVal() throws Exception {
        this.mockMvc.perform(post("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.header("prop_value", "pwd123")
                        .header("memberId", "30")
                        .content("{\"playListId\":\"1\", \"musicUrl\":\"Dt-WNXvN2Zs\", \"thumbnail\":\"/rain.png\", \"playtime\":\"02:40\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("헤더에 prop_value만 있고 memberId는 없는 경우")
    public void headerDoesNotHaveMemberId() throws Exception {
        this.mockMvc.perform(post("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .header("prop_value", "pwd123")
                        //.header("memberId", "30")
                        .content("{\"playListId\":\"1\", \"musicUrl\":\"Dt-WNXvN2Zs\", \"thumbnail\":\"/rain.png\", \"playtime\":\"02:40\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("헤더에 prop val도 있고 memberId도 있는 경우")
    public void headerHavePropVal() throws Exception {
        this.mockMvc.perform(post("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .header("prop_value", "pwd123")
                        .header("memberId", "1")
                        .content("{\"playListId\":\"1\", \"musicUrl\":\"Dt-WNXvN2Zs\", \"thumbnail\":\"/rain.png\", \"playtime\":\"02:40\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
