package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.ResetUserPasswordRequest;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.repositories.UserRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by alopatin on 16-May-17.
 */
@Component
public class ResetUserPasswordValidator extends BaseValidator {
    private final UserRepository userRepository;

    @Autowired
    ResetUserPasswordValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ApiException {
        User currentUser = ParamsHelper.getAs(params,0);
        ResetUserPasswordRequest request = ParamsHelper.getAs(params,1);

        Assert.isTrue(currentUser!=null,"can't find current user");
        Assert.isTrue(request!=null,"request is empty");

        Long userId = request.getUserId();
        String newPassword = request.getNewPassword();

        Assert.isTrue(userId!=null, "can't find requested user");

        User requestedUser = userRepository.findOne(userId);
        Assert.isTrue(requestedUser != null, "can't find requested user");

        Assert.isTrue(!StringUtils.isEmpty(newPassword),"new password is required");
        Assert.isTrue(newPassword.length() > 5, "min password length is 6");
    }
}
