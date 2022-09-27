package com.tomcat.controller;

import com.tomcat.exception.ExpiredTokenException;
import com.tomcat.exception.InvalidEmailFormatException;
import com.tomcat.exception.InvalidTokenException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.Email;
import com.tomcat.model.Password;
import com.tomcat.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Email email) throws UserNotFoundException, MessagingException, InvalidEmailFormatException {
        passwordService.sendEmail(email.getEmail());
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }

    @PostMapping("/savePassword")
    public ResponseEntity<String> savePassword(@RequestParam("token") String token,@Valid @RequestBody Password password) throws UserNotFoundException, InvalidTokenException, ExpiredTokenException {
        passwordService.saveNewPassword(token, password);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }
}
