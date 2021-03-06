package com.dj.server.api.playlist.entity;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.playlist.model.dto.request.PlayListModifyRequestDTO;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("회원 재생 목록을 만드는 테스트 클래스")
@DataJpaTest
public class MemberPlayListEntityTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository memberPlayListRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy memberPlayListDummy = PlayListDummy.getInstance();

    private static Member member;

    @BeforeEach
    public void setUp() {
        Member member = memberRepository.save(memberDummy.toEntity());
        PlayList memberPlayList = memberPlayListRepository.save(memberPlayListDummy.toEntity(member));

        assertThat(memberPlayList.getPlayListName()).isEqualTo(memberPlayListDummy.getPlayListName());
    }

/*    @Test
//    @Order(1)
//    @DisplayName("재생목록을 생성합니다.")
//    public void createPlayList() {
//
//        Member member = memberRepository.save(memberDummy.toEntity());
//        MemberPlayList memberPlayList = memberPlayListRepository.save(memberPlayListDummy.toEntity(member));
//
//        assertThat(memberPlayList.getPlayListName()).isEqualTo(memberPlayListDummy.getPlayListName());
//
//    }
  */
    @Test
    @Order(2)
    @DisplayName("재생목록 이름을 변경합니다.")
    public void updatePlayListName() {

        String changePlayListName = "POP";

        PlayList memberPlayList = memberPlayListRepository.findAll().get(0);
        String notUpdatePlayListName = memberPlayList.getPlayListName();

        memberPlayList.updatePlayList(PlayListModifyRequestDTO.builder().modifyPlayListName(changePlayListName).build());


        PlayList updatePlayList = memberPlayListRepository.findById(memberPlayList.getPlayListId()).orElseThrow(() -> new NullPointerException("회원이 존재 하지 않습니다."));

        assertThat(updatePlayList.getPlayListName()).isEqualTo(changePlayListName);
        assertThat(updatePlayList.getPlayListName()).isNotEqualTo(notUpdatePlayListName);

    }

    @Test
    @Order(2)
    @DisplayName("재생목록 삭제 테스트입니다.")
    public void deletePlayList() {

        PlayList memberPlayList = memberPlayListRepository.findAll().get(0);
        Long memberPlayListId = memberPlayList.getPlayListId();

        memberPlayListRepository.delete(memberPlayList);


        assertThrows(NoSuchElementException.class, () -> memberPlayListRepository.findById(memberPlayListId).orElseThrow(NoSuchElementException::new));

    }



}
