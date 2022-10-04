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
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Column(name = "created_time")
    private OffsetDateTime createdTime;

    @Column(name = "user_id")
    private Long userId;

    public PostEntity(String text, OffsetDateTime createdTime, Long userId) {
        this.text = text;
        this.createdTime = createdTime;
        this.userId = userId;
    }
}
