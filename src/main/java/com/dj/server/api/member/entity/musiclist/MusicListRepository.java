package com.dj.server.api.member.entity.musiclist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicListRepository extends JpaRepository<MusicList, Long> {
}
