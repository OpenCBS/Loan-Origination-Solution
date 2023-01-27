package com.opencbs.services;

import com.opencbs.domain.NotificationHistory;
import com.opencbs.domain.enums.NotificationType;
import com.opencbs.repositories.MessageRepository;
import com.opencbs.repositories.NotificationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final MessageRepository messageRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    public EmailService(JavaMailSender emailSender,
                        MessageRepository messageRepository,
                        NotificationHistoryRepository notificationHistoryRepository) {
        this.emailSender = emailSender;
        this.messageRepository = messageRepository;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void init() {
        this.messageRepository.findByIsSentFalse().forEach(
                x -> {
                    if (x.getSendAt().isAfter(LocalDateTime.now())) {
                        return;
                    }
                    List<String> recipients = Arrays.asList(x.getEmails().split(","));
                    this.sendEmail(x.getSubject(), x.getContent(), recipients);
                    x.setSent(true);
                    this.messageRepository.save(x);
                    this.saveInHistory(x.getSubject(), x.getContent(), recipients);
                }
        );
    }

    private void saveInHistory(String title, String content, List<String> recipients) {
        NotificationHistory history = new NotificationHistory();
        history.setTitle(title);
        history.setContent(content);
        history.setCreatedBy("SYSTEM");
        history.setNotificationType(NotificationType.EMAIL);
        history.setRecipients(String.join(",", recipients));
        history.setCreatedDate(LocalDateTime.now());
        this.notificationHistoryRepository.save(history);
    }

    public void sendEmail(String subject, String content, List<String> recipients) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients.toArray(new String[recipients.size()]));
            helper.setSubject(subject);
            helper.setText(content, true);

            emailSender.send(message);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
