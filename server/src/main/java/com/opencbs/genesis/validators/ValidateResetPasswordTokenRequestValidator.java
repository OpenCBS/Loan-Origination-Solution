package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.ResetUserPasswordToken;
import com.opencbs.genesis.dto.requests.BaseRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.ResetPasswordTokenRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by alopatin on 17-May-17.
 */
@Component
public class ValidateResetPasswordTokenRequestValidator extends  BaseValidator {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    ValidateResetPasswordTokenRequestValidator(ResetPasswordTokenRepository resetPasswordTokenRepository){
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        BaseRequest request = ParamsHelper.getAs(params,0);

        Assert.isTrue(request !=null, "request can't be empty");
        Assert.isTrue(StringHelper.isNotEmpty(request.getData()),"token can't be empty");

        ResetUserPasswordToken token = this.resetPasswordTokenRepository.findOneByToken(request.getData());
        Assert.isTrue(token != null, "can't find token");
    }
}
