package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.ResetUserPasswordToken;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.ResetUserPasswordByTokenRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.ResetPasswordTokenRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by alopatin on 18-May-17.
 */

@Component
public class ResetPasswordByTokenRequestValidator extends BaseValidator {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    ResetPasswordByTokenRequestValidator(ResetPasswordTokenRepository resetPasswordTokenRepository){
        this.resetPasswordTokenRepository=resetPasswordTokenRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        User currentUser = ParamsHelper.getAs(params,0);
        ResetUserPasswordByTokenRequest request = ParamsHelper.getAs(params,1);

        Assert.isTrue(currentUser != null, "can'f find current user");
        Assert.isTrue(request != null, "request can't be null");

        Assert.isTrue(StringHelper.isNotEmpty(request.getNewPassword()), "password can't be empty");
        Assert.isTrue(request.getNewPassword().length() > 5, "min password length is 6");

        Assert.isTrue(StringHelper.isNotEmpty(request.getData()), "token can't be empty");
        ResetUserPasswordToken token = resetPasswordTokenRepository.findOneByToken(request.getData());
        Assert.isTrue(token!=null, "can't find token");
        Assert.isTrue(token.getUser() !=null,"can't find user");
        Assert.isTrue(token.getUser().isEnabled(), "user is not enabled");
    }
}
