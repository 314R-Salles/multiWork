package com.psalles.multiWork.bugReport.service;


import com.psalles.multiWork.bugReport.model.BugReportBody;
import com.psalles.multiWork.bugReport.model.Image;
import com.psalles.multiWork.bugReport.repository.ImageRepository;
import com.psalles.multiWork.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
    @Scheduled(fixedDelay = 1000 * 40)  //40s
    public void sendLastReports() {

        List<Image> images = imageRepository.findAllByReported(false);
        List<ByteArrayResource> attachments = new ArrayList<>();

        images.stream().map(Image::getB64string).forEach(
                b64 -> {
                    byte[] decodedBytes = Base64.getDecoder().decode(b64);
                    attachments.add(new ByteArrayResource(decodedBytes));
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
