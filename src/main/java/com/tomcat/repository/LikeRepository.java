package com.tomcat.repository;

import com.tomcat.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findAllByPostId(Long id);

    @Query(value = "SELECT * FROM likes l WHERE l.user_id=?1 AND l.post_id=?2", nativeQuery = true)
    Optional<LikeEntity> findLike(Long userId, Long postId);

    @Query(value = "SELECT l.* FROM likes l INNER JOIN posts p ON l.post_Id = p.id WHERE p.user_id=?1", nativeQuery = true)
    List<LikeEntity> findAllByOwner(Long userId);
}
