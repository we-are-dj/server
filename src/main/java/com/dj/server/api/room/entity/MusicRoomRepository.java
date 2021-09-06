package com.dj.server.api.room.entity;

import com.dj.server.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRoomRepository extends JpaRepository<MusicRoom, Long> {


    long countByRoomMaster(Member roomMaster);

}
