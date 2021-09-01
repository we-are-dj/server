package com.dj.server.api.room.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRoomRoleRepository extends JpaRepository<Long, MusicRoomRole> {
}
