package com.dj.server.api.musiclist.service;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.musiclist.model.dto.request.MusicListSaveRequestDTO;
import com.dj.server.api.musiclist.model.dto.response.MusicListSaveResponseDTO;
import com.dj.server.api.musiclist.repository.MusicListRepository;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("음악목록 서비스 테스트 클래스")
@SpringBootTest
public class MusicServiceTest {

    @Autowired
    private MusicListService musicListService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private MusicListRepository musicListRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();

    @DisplayName("음악을 재생목록에 저장하는 서비스를 테스트 합니다.")
    @Test
    public void saveMusicListTests() {

        final String playListName = "발라드";
        final String musicUrl = "watch?v=6RQ-bBdASvk";
        final String thumbnail = "thumbnail123";
        final String playtime = "22:08";
        final UUID nickName = UUID.randomUUID();

        Member member = memberRepository.save(memberDummy.customNameToEntity(nickName.toString()));
        PlayList playList = playListRepository.save(playListDummy.toEntityList(member, playListName));

        MusicListSaveRequestDTO musicListSaveRequestDTO = MusicListSaveRequestDTO.builder().playListId(playList.getPlayListId()).musicUrl(musicUrl).thumbnail(thumbnail).playtime(playtime).build();

        MusicListSaveResponseDTO musicListSaveResponseDTO = musicListService.saveMusicList(musicListSaveRequestDTO);

        MusicList musicList = musicListRepository.findById(musicListSaveResponseDTO.getMusicId()).orElseThrow(() -> new NullPointerException("테스트 에러 음악 생성 실패"));

        assertThat(musicListSaveResponseDTO.getMusicId()).isEqualTo(musicList.getMusicId());
        assertThat(musicListSaveResponseDTO.getMusicUrl()).isEqualTo(musicUrl);
        assertThat(musicListSaveResponseDTO.getThumbnail()).isEqualTo(thumbnail);
        assertThat(musicListSaveResponseDTO.getPlaytime()).isEqualTo(playtime);

    }

}
