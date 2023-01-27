package com.opencbs.genesis.dto;

import lombok.Data;

/**
 * Created by Makhsut Isalmov on 23.11.2016.
 */
@Data
public class PermissionDto {
    private Long roleId;
    private Long userId;
    private String name;

    public boolean isRole(){
        return getRoleId() != null;
    }
}
