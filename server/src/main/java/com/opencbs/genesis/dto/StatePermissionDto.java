package com.opencbs.genesis.dto;

import java.util.List;

/**
 * Created by Makhsut Islamov on 23.11.2016.
 */
public class StatePermissionDto extends IdDto{
    private List<PermissionDto> permissions;

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }
}