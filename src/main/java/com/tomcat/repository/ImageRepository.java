package com.tomcat.repository;

import com.tomcat.entity.ProfilImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ProfilImageEntity, Long> {
    Optional<ProfilImageEntity> findByName(String fileName);
    Optional<ProfilImageEntity> findByUserId(Long id);
}
