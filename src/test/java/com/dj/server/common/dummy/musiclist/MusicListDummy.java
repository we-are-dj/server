package com.dj.server.common.dummy.musiclist;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.playlist.entity.PlayList;

/**
 *
 * 음악목록 더미 클래스
 *
 * 여러곳에서 사용하는 음악목록 데이터를 매번 코드를 작성하기에 불편함이 있어 더미 클래스를 생성하였습니다.
 *
 * 싱글톤 패턴을 활용하여 생성하였습니다.
 *
 * @author Informix
 * @created 2021-09-10
 * @since 0.0.1
 */

public class MusicListDummy {

    private static final MusicListDummy instance = new MusicListDummy();

    private MusicListDummy() {}

    public static MusicListDummy getInstance() {
        if (instance == null) return new MusicListDummy();
        return instance;
    }

    public MusicList toEntity(PlayList playList, int musicPlayOrder, String musicUrl) {
        return MusicList.builder()
                .playList(playList)
                .musicPlayOrder(musicPlayOrder)
                .thumbnail("/kakaoProfile.png")
                .playtime("4:12")
                .musicUrl(musicUrl)
                .build();
    }
}
