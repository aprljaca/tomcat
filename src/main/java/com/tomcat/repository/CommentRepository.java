package com.tomcat.repository;

import com.tomcat.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPostId(Long id);

    List<CommentEntity> findAllByUserId(Long userId);

    @Query(value = "SELECT c.* FROM comments c INNER JOIN posts p ON c.post_Id = p.id WHERE p.user_id=?1", nativeQuery = true)
    List<CommentEntity> findAllByOwner(Long userId);

}
