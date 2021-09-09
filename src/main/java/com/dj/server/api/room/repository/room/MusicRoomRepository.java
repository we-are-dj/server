package com.dj.server.api.room.repository.room;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.room.entity.MusicRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRoomRepository extends JpaRepository<MusicRoom, Long>, MusicRoomQueryDSLRepository {


    long countByMember(Member roomMaster);

}
