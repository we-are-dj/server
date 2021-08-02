package com.dj.server.common.dummy.playlist;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.playlist.MemberPlayList;

public class MemberPlayListDummy {

    private final String playListName = "발라드 모음";

    private static final MemberPlayListDummy instance = new MemberPlayListDummy();

    private MemberPlayListDummy() {

    }

    public static MemberPlayListDummy getInstance() {
        if(instance == null) {
            return new MemberPlayListDummy();
        }

        return instance;
    }

    public String getPlayListName() {
        return playListName;
    }

    public MemberPlayList toEntity(Member member) {
        return MemberPlayList.builder()
                .member(member)
                .playListName(this.playListName)
                .build();
    }

}
