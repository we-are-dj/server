package com.dj.server.api.playlist.repository;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.playlist.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 재생목록 Repository
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {

    List<PlayList> findByMember(Member member);

}
