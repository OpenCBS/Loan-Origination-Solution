package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.FieldSectionDto;
import com.opencbs.genesis.dto.requests.FieldValue;
import com.opencbs.genesis.exceptions.ApiException;

import java.util.List;

/**
 * Created by Makhsut Islamov on 06.12.2016.
 */
public interface ApplicationFieldService {
    List<FieldSectionDto> getCurrentStateFields(Long id, User principal) throws ApiException;
    List<FieldSectionDto> getAllFields(Long id) throws ApiException;
    List<FieldSectionDto> updateCurrentStateFields(Long id, List<FieldValue> fieldValues, User principal) throws ApiException;
    List<FieldSectionDto> updateFields(Long id, List<FieldValue> fieldValues) throws ApiException;
}
