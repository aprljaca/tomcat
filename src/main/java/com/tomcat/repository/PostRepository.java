package com.tomcat.repository;

import com.tomcat.entity.PostEntity;
import com.tomcat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository<PostEntity, Long> {
}
