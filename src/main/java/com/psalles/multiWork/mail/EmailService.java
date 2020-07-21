package com.psalles.multiWork.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendBugReports(List<ByteArrayResource> attachments) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("multiwork");
        helper.setTo("thehauntedcomputer@gmail.com");
        helper.setSubject("subject");
        helper.setText("Liste des bug reports");

        attachments.forEach(attachment -> {
            try {
                helper.addAttachment("Bug-report-" + attachments.indexOf(attachment), attachment);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        emailSender.send(message);
    }

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("multiwork");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}