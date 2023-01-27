package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Action;
import com.opencbs.genesis.domain.Activity;
import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;

import java.util.List;

/**
 * Created by Makhsut Islamov on 30.03.2017.
 */
public interface ActivityService {
    void execute(Action action, List<Activity> activities, Application application, User currentUser);
}
