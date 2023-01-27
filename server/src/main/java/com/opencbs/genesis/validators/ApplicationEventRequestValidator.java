package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by alopatin on 03-Jul-17.
 */

@Component
public class ApplicationEventRequestValidator extends BaseValidator {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationEventRequestValidator(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        Long applicationId = ParamsHelper.getAs(params,0);

        Assert.notNull(applicationId,"application id can'b be empty");
        Application application = this.applicationRepository.findOne(applicationId);

        Assert.notNull(application,"application not found");
    }
}
