package com.dj.server.api.member.entity.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * 재생목록 Repository
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@Repository
public interface MemberPlayListRepository extends JpaRepository<MemberPlayList, Long> {
}
