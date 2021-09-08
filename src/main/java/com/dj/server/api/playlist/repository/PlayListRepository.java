package com.dj.server.api.playlist.repository;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.playlist.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * 재생목록 Repository
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long>, PlayListQueryDSLRepository {

    //검증용 회원 ID와 재생목록 id로 재생목록이 있는지 검사합니다.
    Optional<PlayList> findByPlayListIdAndMember(Long playListId, Member member);

    /*
        use 사용값을 변경하면 기존에있는 사용값을 N으로 바꿔주기위하여 기존사용하고있는 재생목록을 가져옵니다

        이 쿼리는 위에서 이미 검증을 하였기에 MemberId , USE 로만 탐색합니다.
     */
    Optional<PlayList> findByMemberAndUse(Member member, String use);

}
