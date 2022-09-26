package com.tomcat.service;

import javax.mail.MessagingException;

public interface EmailService {
    void send(String to, String text) throws MessagingException;
}
