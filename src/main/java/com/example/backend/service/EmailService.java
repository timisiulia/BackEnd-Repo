package com.example.backend.service;

import com.example.backend.model.email.Email;
import com.example.backend.model.request.email.EmailRequest;
import com.example.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {


    private User user;


    public void sendEmail(EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        Email email = new Email();
        email.setSender("timis.iulia.for.licenta@gmail.com");
        email.setRecipient(emailRequest.getRecipient());
        email.setSubject(emailRequest.getSubject());
        email.setContent(emailRequest.getContent());

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("timis.iulia.for.licenta@gmail.com", "xivarzpjhzitzmfk");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email.getSender(), "PrimeTime"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        Transport.send(message);
    }}
