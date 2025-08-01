package com.econ.managify.services;

import com.econ.managify.exceptions.EmailException;
import com.econ.managify.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendEmailWithToken(String userEmail, String link) throws EmailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Hey, Join Us!";
        String text = "Click the link to join us (our team):" + link;
        try {
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setTo(userEmail);
        } catch (MessagingException e) {
            throw new EmailException(e);
        }


        try {
            javaMailSender.send(mimeMessage);
        } catch (EmailException  e) {
            throw new EmailException("Failed to send email");
        }
    }
}
