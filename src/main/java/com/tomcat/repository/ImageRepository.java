package com.tomcat.repository;

import com.tomcat.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    Optional<ProfileImageEntity> findByName(String fileName);
    Optional<ProfileImageEntity> findByUserId(Long id);
}
