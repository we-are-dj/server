package com.dj.server.api.room.repository;

import com.dj.server.api.room.entity.AccessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessUserRepository extends JpaRepository<Long, AccessUser> {
}
