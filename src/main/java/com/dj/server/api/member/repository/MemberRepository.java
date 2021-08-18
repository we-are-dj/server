package com.dj.server.api.member.repository;

import com.dj.server.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * 회원 Repository
 *
 * @author JaeHyun
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryDSLRepository {
    Optional<Member> findByMemberSnsId(String memberSnsId);
    int countByMemberIdAndMemberNickName(Long memberId, String nickname);
}
