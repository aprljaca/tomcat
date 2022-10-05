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
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_time")
    private OffsetDateTime createdTime;

    public LikeEntity(Long postId, Long userId, OffsetDateTime createdTime) {
        this.postId = postId;
        this.userId = userId;
        this.createdTime = createdTime;
    }
}