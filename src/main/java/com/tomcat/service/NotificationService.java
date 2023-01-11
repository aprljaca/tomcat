package com.tomcat.service;

import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.Notification;
import com.tomcat.model.NotificationNumbers;
import com.tomcat.model.User;

public interface NotificationService {
    NotificationNumbers getNotificationNumber(User mapUserEntityToUserDto) throws UserNotFoundException;

    void increaseUnreadFollow(User user) throws UserNotFoundException;

    void increaseUnreadComment(User user) throws UserNotFoundException;

    void increaseUnreadLike(User user) throws UserNotFoundException;

    void resetAllUnreadNotification(User user) throws UserNotFoundException, BadRequestException;

    Notification geNotifications(User user) throws UserNotFoundException;
}
