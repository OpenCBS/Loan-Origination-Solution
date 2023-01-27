package com.opencbs.genesis.helpers;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.enums.SystemPermissions;

/**
 * Created by user on 16.12.2016.
 */
public class SystemPermissionHelper {
    public static boolean hasPermission(User user, SystemPermissions permission){
        return ListHelper.findFirstOrDefault(user.getRole().getPermissions(),
                x -> x.equals(permission) || x.equals(SystemPermissions.ALL)) != null;
    }
}