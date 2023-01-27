package com.opencbs.genesis.validators;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by alopatin on 18-May-17.
 */

@Component
public class UserPhotoValidator extends BaseValidator {
    @Override
    protected void validateInternal(Object... params) throws ApiException {
        User currentUser = ParamsHelper.getAs(params, 0);
        MultipartFile file = ParamsHelper.getAs(params, 1);

        Assert.notNull(currentUser,"can't find current user");
        Assert.notNull(file, "file can not be null");
        Assert.isTrue(!file.isEmpty(), "failed to store empty file " + file.getOriginalFilename());
    }
}
