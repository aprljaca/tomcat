package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class NotificationNumbers {
    private Long id;
    private Long userId;
    private Long unreadNotification;
    private Long unreadFollow;
    private Long unreadComment;
    private Long unreadLike;

}
