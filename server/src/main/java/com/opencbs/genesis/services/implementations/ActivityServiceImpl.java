package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.converters.ApplicationConverter;
import com.opencbs.genesis.domain.Action;
import com.opencbs.genesis.domain.Activity;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.ApplicationSyncDto;
import com.opencbs.genesis.services.ActivityService;
import com.opencbs.genesis.services.EmailNotificationService;
import com.opencbs.genesis.services.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by Makhsut Islamov on 30.03.2017.
 */
@Service
public class ActivityServiceImpl extends BaseService implements ActivityService {
    private RestClientService restClientService;
    private EmailNotificationService emailNotificationService;

    @Autowired
    public ActivityServiceImpl(RestClientService restClientService, EmailNotificationService emailNotificationService) {
        this.restClientService = restClientService;
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public void execute(Action action, List<Activity> activities, Application application, User currentUser) {
        activities.forEach(activity -> executeInternal(action,activity, application, currentUser));
    }

    private void executeInternal(Action action, Activity activity, Application application,User currentUser) {
        switch(activity.getActivityType()) {
            case SEND_DATA:
                sendData(activity, application,currentUser);
                break;
            case SEND_NOTIFICATION:
                sendNotification(action, activity,application);
                break;
            default:
                throw new NotImplementedException();
        }
    }

    private void sendData(Activity activity, Application application, User currentUser) {
        ApplicationSyncDto dto = ApplicationConverter.toSyncDto(application);
        dto.setUserName(currentUser.getUsername());
        restClientService.post(activity.getContent(), dto, String.class);
    }

    private void sendNotification(Action action,Activity activity, Application application){
        emailNotificationService.stateHasBeenChanged(action,activity,application);
    }
}
