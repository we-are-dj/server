package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.controller.MainControllerAdvice;
import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.musiclist.repository.MusicListRepository;
import com.dj.server.api.musiclist.service.MusicListService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataJpaTest(showSql = false)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class MusicListControllerTest {

    private MockMvc mockMvc;

    //더미 클래스
    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();
    private final MusicListDummy musicListDummy = MusicListDummy.getInstance();

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MusicListRepository musicListRepository;

    @Autowired
    private PlayListRepository playListRepository;

    @InjectMocks
    private MusicListController musicListController;

    private PlayList playList = new PlayList();

    @BeforeEach
    public void setup() {
        MusicListService musicListService = new MusicListService(musicListRepository, playListRepository);
        musicListController = new MusicListController(musicListService);

        Long memberId = memberRepository.save(memberDummy.toEntity()).getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("회원 아이디 생성 실패"));
        Long playlistId = playListRepository.save(playListDummy.toEntity(member)).getPlayListId();
        playList = playListRepository.findById(playlistId).orElseThrow(() -> new NullPointerException("플레이리스트 생성 실패"));

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
    @DisplayName("음악 재생 순서 변경 요청이 왔으나 요청에 파라매터가 하나도 없는 경우")
    @Transactional
    public void modifyMusicListPlayOrderDoesNotHaveAllParameters() throws Exception {

        this.mockMvc
                .perform(patch("/v1/musicList")
                                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.param("playListId", "1")
                        //.param("musicIds", "1")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("음악 재생 순서 변경 요청이 왔으나 요청에 파라매터가 부족한 경우")
    public void modifyMusicListPlayOrderDoesNotHaveSomeParameter() throws Exception {

        // does not contain musicIds parameter
        this.mockMvc
                .perform(patch("/v1/musicList")
                                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                                .param("playListId", "1")
                        //.param("musicIds", "1")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        // does not contain playListId parameter
        this.mockMvc
                .perform(patch("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.param("playListId", "1")
                        .param("musicIds", "1")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("음악 재생 순서 변경 요청 테스트")
    @Transactional
    public void modifyMusicListPlayOrderTest() throws Exception {
        musicListRepository.findByPlayList(playList).forEach(e -> System.out.println("e.getMusicPlayOrder(): " + e.getMusicPlayOrder() + ", e.getMusicUrl(): " + e.getMusicUrl()));
        this.mockMvc
                .perform(patch("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("playListId", "1")
                        .param("musicIds", "5, 3, 2, 4, 1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].musicUrl").value("UbOCUMy4QqE"));
        musicListRepository.findByPlayList(playList).forEach(e -> System.out.println("e.getMusicPlayOrder(): " + e.getMusicPlayOrder() + ", e.getMusicUrl(): " + e.getMusicUrl()));
    }

    @Test
    @DisplayName("음악 삭제 요청이 왔으나 요청에 파라매터가 없는 경우")
    public void deleteMusicTest() throws Exception {
        this.mockMvc
                .perform(delete("/v1/musicList")
                                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        //.param("musicIds", "1, 2, 3")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("음악 단건 삭제 요청 테스트")
    public void deleteOneMusicTest() throws Exception {
        // before delete
        assertThat(musicListRepository.count()).isEqualTo(5);
        musicListRepository.findByPlayList(playList).forEach(e -> System.out.println("e.getMusicPlayOrder(): " + e.getMusicPlayOrder() + ", e.getMusicUrl(): " + e.getMusicUrl()));

        this.mockMvc
                .perform(delete("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("musicIds", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("음악목록이 삭제되었습니다."));

        // after delete
        assertThat(musicListRepository.count()).isEqualTo(4);
        musicListRepository.findByPlayList(playList).forEach(e -> System.out.println("e.getMusicPlayOrder(): " + e.getMusicPlayOrder() + ", e.getMusicUrl(): " + e.getMusicUrl()));
    }

    @Test
    @DisplayName("음악 여러 건 삭제 요청 테스트")
    public void deleteSeveralMusicTest() throws Exception {

        // before delete
        assertThat(musicListRepository.count()).isEqualTo(5);
        musicListRepository.findByPlayList(playList).forEach(e -> System.out.println("e.getMusicPlayOrder(): " + e.getMusicPlayOrder() + ", e.getMusicUrl(): " + e.getMusicUrl()));

        this.mockMvc
                .perform(delete("/v1/musicList")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .param("musicIds", "1, 2, 3")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("음악목록이 삭제되었습니다."));

        // before delete
        assertThat(musicListRepository.count()).isEqualTo(2);
        musicListRepository.findByPlayList(playList).forEach(e -> System.out.println("e.getMusicPlayOrder(): " + e.getMusicPlayOrder() + ", e.getMusicUrl(): " + e.getMusicUrl()));
    }
}