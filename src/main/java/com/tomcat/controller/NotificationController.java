package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.Notification;
import com.tomcat.model.NotificationNumbers;
import com.tomcat.service.NotificationService;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class NotificationController {

    private final NotificationService notificationService;

    private final Mapper mapper;

    @GetMapping("/notificationNumber")
    public ResponseEntity<NotificationNumbers> getNotificationNumber(@AuthenticationPrincipal UserEntity userEntity) throws UserNotFoundException {
        NotificationNumbers notificationNumbers = notificationService.getNotificationNumber(mapper.mapUserEntityToUserDto(userEntity));
        return new ResponseEntity<>(notificationNumbers, HttpStatus.OK);
    }

    @PostMapping("/notificationNumberReset")
    public ResponseEntity<?> resetNotificationNumber(@AuthenticationPrincipal UserEntity userEntity) throws UserNotFoundException, BadRequestException {
        notificationService.resetAllUnreadNotification(mapper.mapUserEntityToUserDto(userEntity));
        return new ResponseEntity<>("Notification number reset successfully ", HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public Notification getNotification(@AuthenticationPrincipal UserEntity userEntity) throws UserNotFoundException {
        return notificationService.geNotifications(mapper.mapUserEntityToUserDto(userEntity));
    }
}
