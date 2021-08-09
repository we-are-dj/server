package com.dj.server.api.musiclist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * 재생목록에 대한 플레이리스트 Repository
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@Repository
public interface MusicListRepository extends JpaRepository<MusicList, Long> {
}
