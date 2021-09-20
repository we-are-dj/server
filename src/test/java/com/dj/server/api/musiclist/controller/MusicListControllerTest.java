package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.controller.MainControllerAdvice;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.musiclist.repository.MusicListRepository;
import com.dj.server.api.musiclist.service.MusicListService;
import com.dj.server.api.musiclist.service.youtube.request.YoutubeMusicRequest;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.musiclist.MusicListDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MusicListControllerTest {

    private static MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MusicListRepository musicListRepository;

    @Autowired
    PlayListRepository playListRepository;

    //더미 클래스
    final MemberDummy memberDummy = MemberDummy.getInstance();
    final PlayListDummy playListDummy = PlayListDummy.getInstance();
    final MusicListDummy musicListDummy = MusicListDummy.getInstance();

    @InjectMocks
    private MusicListController musicListController;

    MusicListService musicListService;

    @BeforeEach
    public void setup() {
        MusicListService musicListService = new MusicListService(musicListRepository, playListRepository, new YoutubeMusicRequest());
        musicListController = new MusicListController(musicListService);

        Long memberId = memberRepository.save(memberDummy.toEntity()).getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("회원 아이디 생성 실패"));
        Long playlistId = playListRepository.save(playListDummy.toEntity(member)).getPlayListId();
        PlayList playList = playListRepository.findById(playlistId).orElseThrow(() -> new NullPointerException("플레이리스트 생성 실패"));

        // dummy for testing modifyMusicListPlayOrderTest() method
        int nextMusic = 0;
        musicListRepository.save(musicListDummy.toEntity(playList, ++nextMusic, "UbOCUMy4QqE"));
        musicListRepository.save(musicListDummy.toEntity(playList, ++nextMusic, "Dc574UTsO5g"));
        musicListRepository.save(musicListDummy.toEntity(playList, ++nextMusic, "uuJ-SCwk7Z0"));
        musicListRepository.save(musicListDummy.toEntity(playList, ++nextMusic, "tf2VslhpWHI"));
        musicListRepository.save(musicListDummy.toEntity(playList, ++nextMusic, "1g3Toj4-RMg"));

        mockMvc = MockMvcBuilders.standaloneSetup(musicListController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new MusicListControllerAdvice(), new MainControllerAdvice())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("음악 재생 순서 변경 요청이 왔으나 요청에 파라매터가 하나도 없는 경우")
    public void modifyMusicListPlayOrderDoesNotHaveAllParameters() throws Exception {

        mockMvc.perform(patch("/v1/musicList")
                                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.param("playListId", "1")
                        //.param("musicIds", "1")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(2)
    @DisplayName("음악 재생 순서 변경 요청이 왔으나 요청에 파라매터가 부족한 경우")
    public void modifyMusicListPlayOrderDoesNotHaveSomeParameter() throws Exception {

        // does not contain musicIds parameter
        mockMvc.perform(patch("/v1/musicList")
                                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                                .param("playListId", "1")
                        //.param("musicIds", "1")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        // does not contain playListId parameter
        mockMvc.perform(patch("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.param("playListId", "1")
                        .param("musicIds", "1")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    @DisplayName("음악 재생 순서 변경 요청 테스트")
    public void modifyMusicListPlayOrderTest() throws Exception {
        mockMvc.perform(patch("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("playListId", "1")
                        .param("musicIds", "5, 3, 2, 4, 1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].musicUrl").value("UbOCUMy4QqE"));
        memberRepository.deleteAll();
        playListRepository.deleteAll();
        musicListRepository.deleteAll();
    }

    @Test
    @Order(4)
    @DisplayName("음악 삭제 요청이 왔으나 요청에 파라매터가 없는 경우")
    public void deleteMusicTest() throws Exception {
        mockMvc.perform(delete("/v1/musicList")
                                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.param("musicIds", "1, 2, 3")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    @DisplayName("음악 단건 삭제 요청 테스트")
    public void deleteOneMusicTest() throws Exception {
        mockMvc.perform(delete("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("musicIds", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("음악목록이 삭제되었습니다."));
    }

    @Test
    @Order(6)
    @DisplayName("음악 여러 건 삭제 요청 테스트")
    public void deleteSeveralMusicTest() throws Exception {
        mockMvc.perform(delete("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("musicIds", "1, 3, 4")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("음악목록이 삭제되었습니다."));
    }

}