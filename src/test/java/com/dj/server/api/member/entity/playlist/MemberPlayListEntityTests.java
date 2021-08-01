package com.dj.server.api.member.entity.playlist;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.MemberRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.MemberPlayListDummy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("회원 재생 목록을 만드는 테스트 클래스")
@DataJpaTest
public class MemberPlayListEntityTests {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPlayListRepository memberPlayListRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final MemberPlayListDummy memberPlayListDummy = MemberPlayListDummy.getInstance();

    private static Member member;

    @BeforeEach
    public void setUp() {
        Member member = memberRepository.save(memberDummy.toEntity());
        MemberPlayList memberPlayList = memberPlayListRepository.save(memberPlayListDummy.toEntity(member));

        assertThat(memberPlayList.getPlayListName()).isEqualTo(memberPlayListDummy.getPlayListName());
    }

//    @Test
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
    
    @Test
    @Order(2)
    @DisplayName("재생목록 이름을 변경합니다.")
    public void updatePlayListName() {

        String changePlayListName = "POP";

        MemberPlayList memberPlayList = memberPlayListRepository.findAll().get(0);
        String notUpdatePlayListName = memberPlayList.getPlayListName();


        memberPlayList.updatePlayListName(changePlayListName);


        MemberPlayList updatePlayList = memberPlayListRepository.findById(memberPlayList.getPlayListId()).orElseThrow(() -> new NullPointerException("회원이 존재 하지 않습니다."));

        assertThat(updatePlayList.getPlayListName()).isEqualTo(changePlayListName);
        assertThat(updatePlayList.getPlayListName()).isNotEqualTo(notUpdatePlayListName);

    }

    @Test
    @Order(2)
    @DisplayName("재생목록 삭제 테스트르 입니다.")
    public void deletePlayList() {

        MemberPlayList memberPlayList = memberPlayListRepository.findAll().get(0);
        Long memberPlayListId = memberPlayList.getPlayListId();

        memberPlayListRepository.delete(memberPlayList);


        assertThrows(NoSuchElementException.class, () -> memberPlayListRepository.findById(memberPlayListId).get());

    }



}
