package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.UpdateUserPasswordRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by alopatin on 24-Apr-17.
 */

@Component
public class UpdateUserPasswordValidator extends BaseValidator {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UpdateUserPasswordValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        User currentUser = ParamsHelper.getAs(params,0);
        UpdateUserPasswordRequest request = ParamsHelper.getAs(params,1);

        Assert.notNull(currentUser,"can't find current user");
        Assert.notNull(request,"update user password request can't be empty");

        Assert.isTrue(StringHelper.isNotEmpty(request.getNewPassword()),"new password can't be empty");

        final int minPasswordLength = 6;
        Assert.isTrue(request.getNewPassword().length()>=minPasswordLength, String.format("min password length is %d", minPasswordLength));

        Assert.isTrue(this.passwordEncoder.matches(request.getCurrentPassword(),currentUser.getPassword()),"current password is incorrect");
    }
}
