package com.dj.server.api.room.repository.room;

import com.dj.server.api.room.entity.MusicRoom;
import com.dj.server.api.room.entity.QMusicRoom;
import com.dj.server.api.room.model.dto.request.MusicRoomSearchRequestDTO;
import com.dj.server.api.room.model.dto.response.MusicRoomSearchResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MusicRoomQueryDSLRepositoryImpl extends QuerydslRepositorySupport implements MusicRoomQueryDSLRepository {

    private final QMusicRoom musicRoom = QMusicRoom.musicRoom;

    public MusicRoomQueryDSLRepositoryImpl() {
        super(MusicRoom.class);
    }


    /**
     *
     * Music Room 을 검색합니다.
     * 조건 : 유저 수 가 많은 순으로 검새합니다.
     *
     * @param musicRoomSearchRequestDTO
     * @return
     */
    @Override
    public List<MusicRoomSearchResponseDTO> findByMusicRoomSearchList(MusicRoomSearchRequestDTO musicRoomSearchRequestDTO) {

        //동적 쿼리를 위한 준비

        BooleanBuilder builder = new BooleanBuilder();

        if(musicRoomSearchRequestDTO.getRoomName() != null) {
            for(String search : musicRoomSearchRequestDTO.getRoomName().split(" ")) { // 팝 송 < 이런식으로 올수도 있으니 쪼개줍니다.
                builder.or(musicRoom.roomName.contains(search)); // 포함 %% contains 또는 like
            }
        }

        JPQLQuery<MusicRoomSearchResponseDTO> jpaQuery = from(musicRoom).where(builder).select(Projections.constructor(MusicRoomSearchResponseDTO.class,
                musicRoom.roomId, musicRoom.roomName, musicRoom.roomUserCount));

        jpaQuery.offset((musicRoomSearchRequestDTO.getPageNo() - 1) * 10L).limit(10); // 페이징

        return jpaQuery.fetch();
    }
}
