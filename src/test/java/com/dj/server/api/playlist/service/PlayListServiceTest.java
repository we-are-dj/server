package com.dj.server.api.playlist.service;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.model.dto.request.PlayListModifyRequestDTO;
import com.dj.server.api.playlist.model.dto.request.PlayListSaveRequestDTO;
import com.dj.server.api.playlist.model.dto.response.PlayAllListResponseDTO;
import com.dj.server.api.playlist.model.dto.response.PlayListModifyResponseDTO;
import com.dj.server.api.playlist.model.dto.response.PlayListSaveResponseDTO;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("재생목록 테스트 클래스 입니다.")
@SpringBootTest
public class PlayListServiceTest {

    @Autowired
    private PlayListService playListService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository playListRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy playListDummy = PlayListDummy.getInstance();

    private String[] playArr = {"발라드", "POP", "아이돌노래", "클래식"};

    @DisplayName("회원의 재생목록을 가져오는 서비스를 테스트합니다.")
    @Test
    public void fetchMemberPlayListTests() {

        Member member = memberRepository.save(memberDummy.customNameToEntity("PlayListTest"));

        for(String playListName : playArr) {
            playListRepository.save(playListDummy.toEntityList(member, playListName));
        }

        List<PlayAllListResponseDTO> playListResponseDTOList = playListService.fetchMemberAllPlayList(member.getMemberId());

        assertThat(playListResponseDTOList.size()).isEqualTo(playArr.length);
    }

    @DisplayName("재생목록을 저장하는 서비스를 테스트합니다.")
    @Test
    public void playListSave() {

        final String playListName = "아이유 노래 모음";
        final UUID nickName = UUID.randomUUID();

        Member member = memberRepository.save(memberDummy.customNameToEntity(nickName.toString()));

        PlayListSaveRequestDTO playListSaveRequestDTO = new PlayListSaveRequestDTO(playListName);

        PlayListSaveResponseDTO playListSaveResponseDTO = playListService.playListSave(member.getMemberId(), playListSaveRequestDTO);

        assertThat(playListSaveResponseDTO.getPlayListName()).isEqualTo(playListName);

    }

    @DisplayName("재생목록 이름을 변경합니다.")
    @Test
    public void modifyPlayListName() {

        final String modifyPlayListName = "임창정 노래모음";
        final UUID nickName = UUID.randomUUID();

        Member member = memberRepository.save(memberDummy.customNameToEntity(nickName.toString()));

        Long playListId = playListRepository.save(playListDummy.toEntity(member)).getPlayListId();

        //재생목록 이름만 변경합니다
        PlayListModifyRequestDTO modifyPlayListNameRequestDTO = new PlayListModifyRequestDTO(playListId, modifyPlayListName, null);

        //반환값
        PlayListModifyResponseDTO modifyPlayListNameResponseDTO = playListService.modifyPlayList(member.getMemberId(), modifyPlayListNameRequestDTO);

        //검증

        assertThat(modifyPlayListNameResponseDTO.getPlayListName()).isEqualTo(modifyPlayListName);
        assertThat(modifyPlayListNameResponseDTO.getPlayListId()).isEqualTo(playListId);

    }

    @DisplayName("재생목록을 사용 또는 사용안함 변경합니다.")
    @Test
    public void modifyPlayListUse() {

        final String useYes = "Y";
        final String useNo = "N";
        final UUID nickName = UUID.randomUUID();

        Member member = memberRepository.save(memberDummy.customNameToEntity(nickName.toString()));

        //사용중 객체 하나 생성
        Long firstId = playListRepository.save(playListDummy.toEntity(member)).getPlayListId();

        //Request 객체 생성
        PlayListModifyRequestDTO modifyPlayListUseRequestDTO = new PlayListModifyRequestDTO(firstId, null, useNo);

        //반환값
        PlayListModifyResponseDTO modifyPlayListUseResponseDTO = playListService.modifyPlayList(member.getMemberId(), modifyPlayListUseRequestDTO);

        //검증
        assertThat(modifyPlayListUseResponseDTO.getUse()).isEqualTo(useNo);

        /*
            추가 객체 생성
            기존 사용 객체가 미사용으로 변하는지 확인
         */
        //현재 사용객체
        Long secondId = playListRepository.save(playListDummy.toEntity(member)).getPlayListId();

        /*
            기존 사용 안함 객체를 사용 함으로 변경합니다.
            secondId 의 객체는 Y 에서 N 으로 변경되야 합니다.
         */
        //Request 객체 생성
        modifyPlayListUseRequestDTO = new PlayListModifyRequestDTO(firstId, null, useYes);

        modifyPlayListUseResponseDTO = playListService.modifyPlayList(member.getMemberId(), modifyPlayListUseRequestDTO);

        //기존 재생목록 가져옴 N 이나와야 하는 재생목록
        PlayList playList = playListRepository.findById(secondId).orElseThrow(NullPointerException::new);

        assertThat(playList.getUse()).isEqualTo(useNo);
        assertThat(modifyPlayListUseResponseDTO.getUse()).isEqualTo(useYes);
    }


}
