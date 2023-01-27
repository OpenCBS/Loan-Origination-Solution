package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.WorklogRequest;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by Mahsut Islamov on 21.11.2016.
 */
@Component
public class WorklogValidator extends BaseValidator{
    private final ApplicationRepository applicationRepository;

    @Autowired
    public WorklogValidator(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ValidationException {
        Long applicationId = ParamsHelper.getAs(params, 0);
        WorklogRequest worklog = ParamsHelper.getAs(params, 1);
        User principal = ParamsHelper.getAs(params, 2);

        Application application = applicationRepository.findOne(applicationId);
        Assert.notNull(application, String.format("Application with {id: %d} not found.", applicationId));

        Assert.isTrue(worklog.getSpentHours() != 0, "spentHours is required field.");
        Assert.isTrue(!StringUtils.isEmpty(worklog.getNote()), "note is required field.");

        Assert.isTrue(worklog.getEnteredDate() != null, "date is required field");


        //TODO: check permission to current state
    }
}
