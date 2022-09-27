package com.tomcat.service;

import com.tomcat.entity.PasswordResetEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.*;
import com.tomcat.model.Password;
import com.tomcat.model.User;
import com.tomcat.repository.PasswordResetRepository;
import com.tomcat.repository.UserRepository;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private static final int EXPIRATION_TIME = 30;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PasswordResetRepository passwordResetRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final EmailService emailService;

    private final Mapper mapper;

    @Override
    public void createPasswordResetTokenForUser(User user, String token) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user!");
        }

        Long userId = userEntity.get().getId();

        Optional<PasswordResetEntity> passwordResetEntity = passwordResetRepository.findByUserId(userId);

        if(passwordResetEntity.isPresent()){
            passwordResetRepository.delete(passwordResetEntity.get());
        }

        PasswordResetEntity passwordResetEntity1 = new PasswordResetEntity(token, calculateExpirationTime(), userId);
        passwordResetRepository.save(passwordResetEntity1);
    }

    @Override
    public void validatePasswordResetToken(String token) throws InvalidTokenException, ExpiredTokenException {
        Optional<PasswordResetEntity> passwordResetEntity = passwordResetRepository.findByToken(token);
        if (passwordResetEntity.isEmpty()) {
            throw new InvalidTokenException("Invalid token!");
        }

        OffsetDateTime calendar = OffsetDateTime.now();
        OffsetDateTime expTime = passwordResetEntity.get().getExpirationTime();
        if (calendar.compareTo(expTime) > 0) {
            passwordResetRepository.delete(passwordResetEntity.get());
            throw new ExpiredTokenException("Token expired!");
        }
    }

    @Override
    public User getUserByPasswordResetToken(String token) throws UserNotFoundException {
        Optional<PasswordResetEntity> passwordResetEntity = passwordResetRepository.findByToken(token);
        if (passwordResetEntity.isEmpty()) {
            throw new UserNotFoundException("User with this token not exist!");
        }

        Long userId = passwordResetEntity.get().getUserId();

        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User not exist!");
        }
        return mapper.mapUserEntityToUserDto(userRepository.findById(userId).get());
    }

    @Override
    public void setNewPassword(User user, String newPassword) throws UserNotFoundException {

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user!");
        }
        userEntity.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(userEntity.get());
    }

    @Override
    public void checkIfValidOldPassword(User user, String oldPassword) throws InvalidOldPasswordException, UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());

        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user!");
        }
        if (!passwordEncoder.matches(oldPassword, userEntity.get().getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password!");
        }
    }

    @Override
    public void sendEmail(String email) throws UserNotFoundException, InvalidEmailFormatException, MessagingException {

        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern regExPattern = Pattern.compile(regex);
        Matcher matcher = regExPattern.matcher(email);

        if (!matcher.matches()) {
            throw new InvalidEmailFormatException("Email format is invalid!");
        }
        User user = userService.findUserByEmail(email);
        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user!");
        }
        String url = getUrl(mapper.mapUserEntityToUserDto(userEntity.get()));
        emailService.send(email, makeContent(url));
    }

    @Override
    public String getUrl(User user) throws UserNotFoundException {
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        return passwordResetTokenMail(token);
    }

    @Override
    public void saveNewPassword(String token, Password password) throws InvalidTokenException, ExpiredTokenException, UserNotFoundException {
        validatePasswordResetToken(token);
        User user = getUserByPasswordResetToken(token);
        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user!");
        }
        setNewPassword(mapper.mapUserEntityToUserDto(userEntity.get()), password.getNewPassword());

        Optional<PasswordResetEntity> passwordResetEntity = passwordResetRepository.findByToken(token);
        if (passwordResetEntity.isEmpty()) {
            throw new InvalidTokenException("Invalid token!");
        }
        passwordResetRepository.delete(passwordResetEntity.get());
    }

    @Override
    public void changePassword(Password password) throws UserNotFoundException, InvalidOldPasswordException {
        User user = userService.findUserByEmail(password.getEmail());
        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user!");
        }
        checkIfValidOldPassword(user, password.getOldPassword());
        setNewPassword(user, password.getNewPassword());
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 15 * 60 * 1000)
    public void deleteExpiredTokens() throws InvalidTokenException {
        List<PasswordResetEntity> entities = passwordResetRepository.findAll();

        for (PasswordResetEntity entity : entities) {

            Optional<PasswordResetEntity> passwordResetEntity = passwordResetRepository.findByToken(entity.getToken());
            if (passwordResetEntity.isEmpty()) {
                throw new InvalidTokenException("Invalid token!");
            }

            OffsetDateTime calendar = OffsetDateTime.now();
            OffsetDateTime expTime = passwordResetEntity.get().getExpirationTime();
            if (calendar.compareTo(expTime) > 0) {
                passwordResetRepository.delete(passwordResetEntity.get());
            }

        }
    }

    private String passwordResetTokenMail(String token) {
        return String.format("http://localhost:3000/savePassword/token=%s", token);
    }

    private OffsetDateTime calculateExpirationTime() {
        return OffsetDateTime.now().plusMinutes(EXPIRATION_TIME);
    }

    private String makeContent(String url) {
        return "Hello,\n"
                + "You have requested to reset your password.\n"
                + "Click the link below to change your password:\n"
                + url + "\n"
                + "Ignore this email if you do remember your password, or you have not made the request.";
    }

}
