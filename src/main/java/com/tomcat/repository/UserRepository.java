package com.tomcat.repository;

import com.tomcat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
    @Query(value="SELECT *  FROM Users ORDER BY random() LIMIT 2", nativeQuery=true)
    List<UserEntity> findRandomProfiles();
}

