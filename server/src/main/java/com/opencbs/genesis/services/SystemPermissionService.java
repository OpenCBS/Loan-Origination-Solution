package com.opencbs.genesis.services;

import com.opencbs.genesis.dto.CodeNameDto;

import java.util.List;

/**
 * Created by Makhsut Islamov on 16.12.2016.
 */
public interface SystemPermissionService {
    List<CodeNameDto> getAll();
}