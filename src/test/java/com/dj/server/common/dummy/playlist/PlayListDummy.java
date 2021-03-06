package com.dj.server.common.dummy.playlist;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.playlist.entity.PlayList;

/**
 *
 * 재생목록 더미 클래스
 *
 * 여러곳에서 사용하는 재생목록 데이터를 매번 코드를 작성하기에 불편함이 있어 더미 클래스를 생성하였습니다.
 *
 * 싱글톤 패턴을 활용하여 생성하였습니다.
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

public class PlayListDummy {

    private final String playListName = "발라드 모음";

    private static final PlayListDummy instance = new PlayListDummy();

    private PlayListDummy() {

    }

    public static PlayListDummy getInstance() {
        if (instance == null) {
            return new PlayListDummy();
        }

        return instance;
    }

    public String getPlayListName() {
        return playListName;
    }

    public PlayList toEntity(Member member) {
        return PlayList.builder()
                .member(member)
                .playListName(this.playListName)
                .use("Y")
                .build();
    }

    public PlayList toEntityList(Member member, String playListName) {
        return PlayList.builder()
                .member(member)
                .playListName(playListName)
                .use("Y")
                .build();
    }

}
