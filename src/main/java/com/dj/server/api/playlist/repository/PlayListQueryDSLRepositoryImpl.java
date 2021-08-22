package com.dj.server.api.playlist.repository;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.entity.QMember;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.entity.QPlayList;
import com.dj.server.api.playlist.model.dto.response.PlayAllListResponseDTO;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PlayListQueryDSLRepositoryImpl extends QuerydslRepositorySupport implements PlayListQueryDSLRepository {

    private final QMember member = QMember.member;
    private final QPlayList playList = QPlayList.playList;

    public PlayListQueryDSLRepositoryImpl() {
        super(PlayList.class);
    }

    /**
     *
     * 회원의 모든 플레이 리스트를 조회합니다.
     *
     * querydsl repository Support 는
     * from 절이 먼저시작됩니다.
     *
     * @param member
     * @return
     */
    @Override
    public List<PlayAllListResponseDTO> findByMemberAllPlayList(Long memberId) {

        return from(playList)
                .innerJoin(member).on(playList.member.eq(member))
                .where(member.memberId.eq(memberId))
                .select(Projections.constructor(PlayAllListResponseDTO.class, // 리턴받을 DTO 클래스를 필드 생성자 주입 하겠다는것.
                        playList.playListId, playList.playListName, playList.use ))
                .fetch();

    }

}
