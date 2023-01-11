package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class Notification {
    private List<ProfileInformation> unreadFollowList;
    private List<NotificationInformation> unreadCommentList;
    private List<NotificationInformation> unreadLikeList;
}
