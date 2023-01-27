package com.opencbs.genesis.validators.helpers;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.permissions.ApplicationStatePermission;
import com.opencbs.genesis.domain.permissions.RoleApplicationStatePermission;
import com.opencbs.genesis.domain.permissions.UserApplicationStatePermission;
import com.opencbs.genesis.exceptions.ForbiddenException;
import com.opencbs.genesis.helpers.ListHelper;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
public class PermissionValidatorHelper {
    @SuppressWarnings("ConstantConditions")
    public static void validateCurrentStatePermissions(Application application, User user) throws ForbiddenException {
        List<ApplicationStatePermission> permissions = application.getApplicationStatePermissions();

        Predicate<ApplicationStatePermission> predicate = x -> ((RoleApplicationStatePermission.class.isInstance(x)
                && ((RoleApplicationStatePermission) x).getRole().getId().equals(user.getRole().getId()))
                || (UserApplicationStatePermission.class.isInstance(x)
                && ((UserApplicationStatePermission) x).getUser().getId().equals(user.getId()))
                && x.getState().getId().equals(application.getCurrentState().getId()));

        if (ListHelper.findFirstOrDefault(permissions, predicate) == null
                && !application.getResponsibleUser().getId().equals(user.getId())) {
            throw new ForbiddenException();
        }
    }
}