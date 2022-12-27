package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.*;
import com.tomcat.model.Password;
import com.tomcat.model.User;

import javax.mail.MessagingException;

public interface PasswordService {
    void createPasswordResetTokenForUser(User user, String token) throws UserNotFoundException;

    void validatePasswordResetToken(String token) throws InvalidTokenException, ExpiredTokenException;

    User getUserByPasswordResetToken(String token) throws UserNotFoundException;

    void setNewPassword(User user, String newPassword) throws UserNotFoundException;

    public void checkIfValidOldPassword(User user, String oldPassword) throws InvalidOldPasswordException, UserNotFoundException;

    void sendEmail(String email) throws UserNotFoundException, MessagingException, InvalidEmailFormatException, MessagingException;

    public String getUrl(User user) throws UserNotFoundException;

    void saveNewPassword(String token, Password password) throws InvalidTokenException, ExpiredTokenException, UserNotFoundException;

    void changePassword(User user, Password password) throws UserNotFoundException, InvalidOldPasswordException;

    void deleteExpiredTokens() throws InvalidTokenException, ExpiredTokenException;
}
