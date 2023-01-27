package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.dto.requests.ApplicationEditRequest;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by Makhsut Islamov on 20.12.2016.
 */
@Component
public class ApplicationEditRequestValidator extends BaseValidator {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationEditRequestValidator(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected void validateInternal(Object ... params) throws ValidationException {
        Long applicationId = ParamsHelper.getAs(params, 0);
        ApplicationEditRequest request = ParamsHelper.getAs(params, 1);

        Assert.isTrue(!StringUtils.isEmpty(request.getName()), "name is required field.");

        Application application = applicationRepository.findOne(applicationId);
        Assert.notNull(application, String.format("Application with {id: %d} not found", applicationId));
    }
}