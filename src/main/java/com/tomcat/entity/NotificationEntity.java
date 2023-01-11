package com.tomcat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "unread_notification")
    private Long unreadNotification;
    @Column(name = "unread_follow")
    private Long unreadFollow;
    @Column(name = "unread_comment")
    private Long unreadComment;
    @Column(name = "unread_like")
    private Long unreadLike;


    public NotificationEntity(Long userId, Long unreadNotification, Long unreadFollow, Long unreadComment, Long unreadLike) {
        this.userId = userId;
        this.unreadNotification = unreadNotification;
        this.unreadFollow = unreadFollow;
        this.unreadComment = unreadComment;
        this.unreadLike  = unreadLike;
    }
}
