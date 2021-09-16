package com.dj.server.api.property;

import com.dj.server.api.common.controller.MainControllerAdvice;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.musiclist.controller.MusicListController;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.api.properties.entity.Property;
import com.dj.server.api.properties.repository.PropertyRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropertyTest {

    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @InjectMocks
    private MusicListController musicListController;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();

    @BeforeEach
    public void setup() {
        Member member = memberRepository.save(memberDummy.toEntity());
        playListRepository.save(playListDummy.toEntity(member));
        propertyRepository.save(new Property(1, "pwd123", "인터셉터 우회"));
        mockMvc = MockMvcBuilders.standaloneSetup(musicListController)
                .setControllerAdvice(new MainControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("헤더에 prop val도 없고 토큰도 없는 경우")
    public void headerDoesNotHavePropVal() throws Exception {
        this.mockMvc.perform(post("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        // .header("prop_value", "pwd123")
                        .content("{\"playListId\":1,\"musicUrl\":\"Dt-WNXvN2Zs\",\"thumbnail\":\"/rain.png\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("헤더에 prop val이 있는 경우")
    public void headerHavePropVal() throws Exception {
        System.out.println("prop.key : " + Objects.requireNonNull(propertyRepository.findByPropValue("pwd123").orElse(null)).getPropKey());
        this.mockMvc.perform(post("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .header("prop_value", "pwd123")
                        .content("{\"playListId\":1, \"musicUrl\":\"Dt-WNXvN2Zs\", \"thumbnail\":\"/rain.png\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
