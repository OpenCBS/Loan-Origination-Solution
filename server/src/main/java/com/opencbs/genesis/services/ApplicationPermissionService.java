package com.opencbs.genesis.services;

import com.opencbs.genesis.dto.StatePermissionDto;
import com.opencbs.genesis.exceptions.ApiException;

import java.util.List;

/**
 * Created by Makhsut Islamov on 13.12.2016.
 */
public interface ApplicationPermissionService {
    List<StatePermissionDto> findBy(Long applicationId);
    List<StatePermissionDto> recreate(Long applicationId, List<StatePermissionDto> permissions) throws ApiException;
}