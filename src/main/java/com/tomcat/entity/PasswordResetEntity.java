package com.tomcat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reset_token")
public class PasswordResetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private OffsetDateTime expirationTime;

    @Column(name = "user_id", unique = true)
    private Long userId;

    public PasswordResetEntity(String token, OffsetDateTime expirationTime, Long userId) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.userId = userId;
    }
}
