package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.*;
import com.tomcat.model.Email;
import com.tomcat.model.Password;
import com.tomcat.service.PasswordService;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class PasswordController {

    private final PasswordService passwordService;
    private final Mapper mapper;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Email email) throws UserNotFoundException, MessagingException, InvalidEmailFormatException {
        passwordService.sendEmail(email.getEmail());
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserEntity userEntity, @Valid @RequestBody Password password) throws UserNotFoundException, InvalidOldPasswordException {
        passwordService.changePassword(mapper.mapUserEntityToUserDto(userEntity), password);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    @PostMapping("/savePassword")
    public ResponseEntity<String> savePassword(@RequestParam("token") String token,@Valid @RequestBody Password password) throws UserNotFoundException, InvalidTokenException, ExpiredTokenException {
        passwordService.saveNewPassword(token, password);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }
}
