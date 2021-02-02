package com.somnus.server.backend.emailApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SomnusMailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    public SomnusMailSender(){}

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendEmailToAdmin(String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(environment.getProperty("spring.mail.username"));
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
