package com.tomcat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final String email;

    private final String subject;

    public EmailServiceImpl(JavaMailSender mailSender, @Value("${spring.mail.from}") String email, @Value("${spring.mail.title}") String subject) {
        this.mailSender = mailSender;
        this.email = email;
        this.subject = subject;
    }

    @Override
    public void send(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}