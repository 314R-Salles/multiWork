package com.psalles.multiWork.bugReport.service;


import com.psalles.multiWork.bugReport.model.BugReportBody;
import com.psalles.multiWork.bugReport.model.Image;
import com.psalles.multiWork.bugReport.repository.ImageRepository;
import com.psalles.multiWork.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("!local")
@Service
public class BugReportService {

    EmailService emailService;
    ImageRepository imageRepository;

    @Autowired
    public BugReportService(ImageRepository imageRepository, EmailService emailService) {
        this.imageRepository = imageRepository;
        this.emailService = emailService;
    }

    public void save(BugReportBody image) {
        cleanB64Prefix(image);
        imageRepository.save(new Image(image.getFromApp(), image.getImage(), LocalDateTime.now(), false));
    }

    // Front send b64 string prefixed by "data:image/octet-stream;base64,"
    private void cleanB64Prefix(BugReportBody body) {
        body.setImage(body.getImage().split(",")[1]);
    }


    // Cron
    @Scheduled(fixedDelayString = "${cron.duration}")
    public void sendLastReports() {

        List<Image> images = imageRepository.findAllByReported(false);
        Map<Long, ByteArrayResource> attachments = new HashMap<>();

        images.forEach(
                image -> {
                    byte[] decodedBytes = Base64.getDecoder().decode(image.getB64string());
                    attachments.put(image.getId(),new ByteArrayResource(decodedBytes));
                });
        if (images.size() > 0) {
            try {
                emailService.sendBugReports(attachments);
                images.forEach(image -> image.setReported(true));
                imageRepository.saveAll(images);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

}
