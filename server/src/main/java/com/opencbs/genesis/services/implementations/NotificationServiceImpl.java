package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.Notification;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.helpers.DateHelper;
import com.opencbs.genesis.repositories.NotificationRepository;
import com.opencbs.genesis.services.NotificationService;
import com.opencbs.genesis.validators.NotificationValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Askat on 12/8/2016.
 */
@Service
public class NotificationServiceImpl extends BaseService implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<Notification> findBy(Long applicationId, Pageable pageable) {
        Criterion criterionDone = Restrictions.eq("application.id", applicationId);
        Criterion criterionApplication = Restrictions.eq("done", false);
        Criterion criterion = Restrictions.and(criterionDone, criterionApplication);
        return notificationRepository.findBy(criterion, pageable);
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findOne(id);
    }

    @Override
    public Page<Notification> findByNotificationDate(String value, Pageable pageable) {
        Date date = DateHelper.convert(value);
        Criterion betweenCriterion = Restrictions.between("notificationDate", DateHelper.getStartOfDay(date), DateHelper.getEndOfDay(date));
        Criterion todoNotification = Restrictions.eq("done", false);
        Criterion criterion = Restrictions.and(betweenCriterion, todoNotification);
        return notificationRepository.findBy(criterion, pageable);
    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        Criterion criterion = Restrictions.eq("done", false);
        return notificationRepository.findBy(criterion, pageable);
    }

    @Override
    @ValidateWith(NotificationValidator.class)
    @Transactional
    public Notification create(Long applicationId, Notification notification, User principal) {
        notification.setApplication(new Application(applicationId));
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Notification notification, Long id) {
        Notification oldNotification = findById(id);
        notification.setApplication(oldNotification.getApplication());
        notification.setId(id);
        return notificationRepository.save(notification);
    }
}
