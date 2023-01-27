package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.BaseRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.services.UserService;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by alopatin on 17-May-17.
 */

@Component
public class CreateResetPasswordTokenRequestValidator extends BaseValidator {
    private final UserService userService;

    @Autowired
    CreateResetPasswordTokenRequestValidator(UserService userService){
        this.userService = userService;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        BaseRequest request = ParamsHelper.getAs(params,0);

        Assert.isTrue(request != null, "request can't be null");
        String email = request.getData();
        Assert.isTrue(StringHelper.isNotEmpty(email), "email can't be empty");
        User user = this.userService.findByEmailAndEnabledTrue(email);
        Assert.isTrue(user !=null, String.format("can't find active user with email '%s'", email));
    }
}
