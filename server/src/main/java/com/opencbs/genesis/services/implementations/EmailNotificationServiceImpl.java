package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.*;
import com.opencbs.genesis.domain.enums.EmailTemplateType;
import com.opencbs.genesis.domain.enums.NotificationType;
import com.opencbs.genesis.dto.EventDto;
import com.opencbs.genesis.dto.EventParticipantsDto;
import com.opencbs.genesis.helpers.DateHelper;
import com.opencbs.genesis.properties.GeneralProperties;
import com.opencbs.genesis.repositories.MessageRepository;
import com.opencbs.genesis.services.EmailNotificationService;
import com.opencbs.genesis.services.EmailTemplateService;
import com.opencbs.genesis.services.NotificationHistoryService;
import com.opencbs.genesis.services.UserService;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by alopatin on 12-Apr-17.
 */

@Service
public class EmailNotificationServiceImpl extends BaseService implements EmailNotificationService {
    private final UserService userService;
    private final GeneralProperties generalProperties;
    private final EmailTemplateService emailTemplateService;
    private final NotificationHistoryService notificationHistoryService;
    private final MessageRepository messageRepository;

    @Autowired
    public EmailNotificationServiceImpl(JavaMailSender emailSender,
                                        UserService userService,
                                        GeneralProperties generalProperties,
                                        EmailTemplateService emailTemplateService,
                                        NotificationHistoryService notificationHistoryService,
                                        MessageRepository messageRepository) {
        this.userService = userService;
        this.generalProperties = generalProperties;
        this.emailTemplateService = emailTemplateService;
        this.notificationHistoryService = notificationHistoryService;
        this.messageRepository = messageRepository;
    }

    @Override
    public void stateHasBeenChanged(Action action, Activity activity, Application application) {
        String content = activity.getContent();

        content = content.replace("Application_Id", application.getId().toString())
                .replace("Application_Name", application.getName())
//                .replace("Client_Name", application.getProfile().getFullName())
                .replace("Initial_Status", action.getOwnerState().getName())
                .replace("New_Status", action.getTransitionState().getName())
                .replace("Site_Url", generalProperties.getSiteUrl());

        List<User> users = userService.findByApplicationAndCurrentState(application);
        if (users == null) {
            //state doesn't have responsible users
            return;
        }
        List<String> emails = users.stream().filter(user -> !StringUtils.isEmpty(user.getEmail())).map(User::getEmail).collect(Collectors.toList());

        String subject = String.format("Application %s state changed to %s", application.getName(), action.getTransitionState().getName());

        if (!CollectionHelper.isEmpty(emails)) {
            this.saveEmail(subject, content, emails);
            this.saveNotificationHistory(NotificationType.EMAIL, subject, content, emails, "SYSTEM");
        }
    }

    @Override
    public void eventCreated(EventDto event, List<EventParticipantsDto> participants, User currentUser) {
        notifyAboutEventChanges(event, participants, currentUser, EmailTemplateType.EVENT_CREATED);
    }

    @Override
    public void eventUpdated(EventDto event, List<EventParticipantsDto> participants, User user) {
        notifyAboutEventChanges(event, participants, user, EmailTemplateType.EVENT_UPDATED);
    }

    @Override
    public void attachmentAdded(ApplicationAttachment applicationAttachment) {
        EmailTemplate emailTemplate = emailTemplateService.findOneByType(EmailTemplateType.ATTACHMENT_ADDED);
        if (emailTemplate == null) {
            return;
        }

        Application application = applicationAttachment.getApplication();
        User responsibleUser = application.getResponsibleUser();
        String title = emailTemplate.getTitle();

        String content = emailTemplate.getContent()
                .replace("Application_Id", application.getId().toString())
                .replace("Application_Name", application.getName())
                .replace("Responsible_User", responsibleUser.getFullName())
                .replace("File_Name", applicationAttachment.getName())
                .replace("Author_Name", applicationAttachment.getCreatedUser().getFullName())
                .replace("Added_Date", DateHelper.toUTCDate(applicationAttachment.getCreatedDate()))
                .replace("Site_Url", generalProperties.getSiteUrl());

        List<String> recipients = new ArrayList<>();
        recipients.add(responsibleUser.getEmail());

        this.saveEmail(title, content, recipients);
        this.saveNotificationHistory(NotificationType.EMAIL, title, content, recipients, "SYSTEM");
    }

    @Override
    public void worklogAdded(Worklog worklog) {
        EmailTemplate emailTemplate = emailTemplateService.findOneByType(EmailTemplateType.WORKLOG_ADDED);
        if (emailTemplate == null) {
            return;
        }

        Application application = worklog.getApplication();
        User responsibleUser = application.getResponsibleUser();
        String title = emailTemplate.getTitle();

        String content = emailTemplate.getContent()
                .replace("Application_Id", application.getId().toString())
                .replace("Application_Name", application.getName())
                .replace("Responsible_User", responsibleUser.getFullName())
                .replace("Added_Date", DateHelper.toUTCDate(worklog.getCreatedDate()))
                .replace("Added_By", worklog.getCreatedUser().getFullName())
                .replace("Site_Url", generalProperties.getSiteUrl());

        List<String> recipients = new ArrayList<>();
        recipients.add(responsibleUser.getEmail());

        this.saveEmail(title, content, recipients);
        this.saveNotificationHistory(NotificationType.EMAIL, title, content, recipients, "SYSTEM");
    }

    @Override
    public void sendTokenForResetPassword(User user, String token) {
        EmailTemplate emailTemplate = emailTemplateService.findOneByType(EmailTemplateType.RESET_PASSWORD_TOKEN_REQUESTED);
        if (emailTemplate == null) {
            return;
        }

        String content = emailTemplate.getContent()
                .replace("Reset_Password_Token", token)
                .replace("Site_Url", generalProperties.getSiteUrl());

        this.saveEmail(emailTemplate.getTitle(), content, user.getEmail());
    }

    private void notifyAboutEventChanges(EventDto event, List<EventParticipantsDto> participants, User currentUser, EmailTemplateType emailTemplateType) {
        if (CollectionHelper.isEmpty(participants)) {
            return;
        }

        EmailTemplate emailTemplate = emailTemplateService.findOneByType(emailTemplateType);
        if (emailTemplate == null) {
            return;
        }

        Date startDate = event.getStart();

        String title = emailTemplate.getTitle();
        String createdBy = event.getCreatedUser().getName();
        String content = emailTemplate.getContent()
                .replace("Created_User", createdBy)
                .replace("Event_Name", event.getTitle())
                .replace("Event_Date", DateHelper.toUTCDate(startDate))
                .replace("Event_Description", event.getDescription())
                .replace("Site_Url", generalProperties.getSiteUrl());

        String time = DateHelper.toUTCTime(startDate);

        if (time.equals("00:00")) {
            content = content.replace("at Event_Time", "");
        } else {
            content = content.replace("Event_Time", time);
        }

        List<String> recipients = participants.stream().filter(participant -> StringHelper.isNotEmpty(participant.getEmail())).map(EventParticipantsDto::getEmail).collect(Collectors.toList());
        this.saveEmail(title, content, recipients);
        this.saveNotifyEmail(event, currentUser);
        this.saveNotificationHistory(NotificationType.EVENT, title, content, recipients, createdBy);
    }

    private void saveNotifyEmail(EventDto event, User currentUser) {

        EmailTemplate emailTemplate = emailTemplateService.findOneByType(EmailTemplateType.NOTIFY);
        if (emailTemplate == null) {
            return;
        }
        Date startDate = event.getStart();
        String createdBy = event.getCreatedUser().getName();
        String content = emailTemplate.getContent()
                .replace("Responsible_User", createdBy)
                .replace("Event_Date", DateHelper.toUTCDate(startDate));

        String time = DateHelper.toUTCTime(startDate);

        if (time.equals("00:00")) {
            content = content.replace("at Event_Time", "");
        } else {
            content = content.replace("Event_Time", time);
        }

        this.messageRepository.deleteByEventIdAndIsSentFalse(event.getId());
        this.saveEmail(emailTemplate.getTitle(), content, currentUser.getEmail(), event.getNotify(), event.getId());
    }

    private EmailMessage saveEmail(String subject, String content, String recipients, Date date, Long eventId) {
        EmailMessage message = new EmailMessage();
        message.setSubject(subject);
        message.setContent(content);
        message.setEmails(recipients);
        message.setSendAt(date);
        message.setSent(false);
        message.setEventId(eventId);
        return this.messageRepository.save(message);
    }

    private EmailMessage saveEmail(String subject, String content, String recipients) {
        return this.saveEmail(subject, content, recipients, DateHelper.localDateTimeToDate(LocalDateTime.now()), null);
    }

    private EmailMessage saveEmail(String subject, String content, List<String> recipients) {
        return this.saveEmail(subject, content, String.join(",", recipients));
    }

    private void saveNotificationHistory(NotificationType notificationType, String title, String content, List<String> recipients, String createdBy) {
        try {
            NotificationHistory notificationHistory = new NotificationHistory();
            notificationHistory.setId(null);
            notificationHistory.setTitle(title);
            notificationHistory.setContent(content);
            notificationHistory.setNotificationType(notificationType);
            notificationHistory.setRecipients(String.join(", ", recipients));
            notificationHistory.setCreatedBy(createdBy);

            this.notificationHistoryService.save(notificationHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
