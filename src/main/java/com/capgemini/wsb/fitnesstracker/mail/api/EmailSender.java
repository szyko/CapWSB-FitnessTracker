package com.capgemini.wsb.fitnesstracker.mail.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.toAddress());
        message.setSubject(emailDto.subject());
        message.setText(emailDto.content());
        javaMailSender.send(message);
    }
}
