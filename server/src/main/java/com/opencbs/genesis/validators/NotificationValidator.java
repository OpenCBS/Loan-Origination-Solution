package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.Notification;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Askat on 12/8/2016.
 */
@Component
public class NotificationValidator extends BaseValidator {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public NotificationValidator(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        Long applicationId = ParamsHelper.getAs(params, 0);
        Notification notification = ParamsHelper.getAs(params, 1);

        Application application = applicationRepository.findOne(applicationId);
        Assert.notNull(application, String.format("Application with {id: %d} not found.", applicationId));
        Assert.isTrue(!notification.getMessage().isEmpty(), "message is required field");
        Assert.isTrue(notification.getNotificationDate() != null, "notification date is required field");
    }
}
