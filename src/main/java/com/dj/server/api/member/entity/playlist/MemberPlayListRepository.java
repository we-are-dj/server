package com.dj.server.api.member.entity.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPlayListRepository extends JpaRepository<MemberPlayList, Long> {
}
