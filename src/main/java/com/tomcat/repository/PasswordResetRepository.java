package com.tomcat.repository;

import com.tomcat.entity.PasswordResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, Long> {
    Optional<PasswordResetEntity> findByToken(String token);
    Optional<PasswordResetEntity> findByUserId(Long id);
}
