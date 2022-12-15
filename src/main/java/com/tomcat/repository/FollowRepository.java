package com.tomcat.repository;

import com.tomcat.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    @Query(value = "SELECT * FROM Follows f WHERE f.follower_id=?1 AND f.following_id=?2", nativeQuery = true)
    Optional<FollowEntity> findFollower(Long followerId, Long followingId);
}
