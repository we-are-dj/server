package com.dj.server.api.musiclist;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.dummy.member.MemberDummy;
import com.dj.server.common.dummy.playlist.PlayListDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("MusicList Table CRUD Test")
@DataJpaTest
public class MusicListTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlayListRepository memberPlayListRepository;

    @Autowired
    private MusicListRepository musicListRepository;

    private final MemberDummy memberDummy = MemberDummy.getInstance();
    private final PlayListDummy memberPlayListDummy = PlayListDummy.getInstance();

//    @BeforeEach
//    public void setUp() {
//        //생성 테스트 합니다.
//
//        Member member = memberRepository.save(memberDummy.toEntity());
//        MemberPlayList memberPlayList = memberPlayListRepository.save(memberPlayListDummy.toEntity(member));
//
//        for(int i=0; i<10; i++) {
//            musicListRepository.save(MusicList.builder()
//                    .musicNo(i + 1)
//                    .musicUrl("youtube/" + i)
//                    .build());
//        }
//    }

    @Test
    @Order(1)
    @DisplayName("노래 하나를 삭제 테스트 합니다.")
    public void deleteMusic() {

        Member member = memberRepository.save(memberDummy.toEntity());

        PlayList memberPlayList = memberPlayListRepository.save(memberPlayListDummy.toEntity(member));

        Long id8 = null;

        for(int i=0; i<10; i++) {
            MusicList musicList = musicListRepository.save(MusicList.builder()
                    .musicNo(i + 1)
                    .musicUrl("youtube/" + i)
                    .build());

            if(i == 8) {
                id8 = musicList.getMusicId();
            }

        }


        //8번 인덱스를 삭제 합니다
        assert id8 != null;
        MusicList musicList = musicListRepository.findById(id8).orElseThrow(() -> new NullPointerException("노래가 존재 하지 않습니다."));

        assertThat(musicList.getMusicId()).isEqualTo(id8);

    }


}
