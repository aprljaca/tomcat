package com.tomcat.repository;

import com.tomcat.entity.PostEntity;
import com.tomcat.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM posts p WHERE p.user_id IN (:usersIdList)", nativeQuery = true)
    List<PostEntity> findyAllByUserIdList(List<Long> usersIdList);

    Optional<PostEntity> findById(Long postId);

}
