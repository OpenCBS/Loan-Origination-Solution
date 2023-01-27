package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.State;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.ApplicationDto;
import com.opencbs.genesis.dto.ApplicationSimpleDto;
import com.opencbs.genesis.dto.requests.ActionRequest;
import com.opencbs.genesis.dto.requests.ApplicationEditRequest;
import com.opencbs.genesis.dto.requests.ApplicationRequest;
import com.opencbs.genesis.exceptions.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
public interface ApplicationService {
    Page<ApplicationSimpleDto> findBy(Map<String, String> filters, Pageable pageable) throws ApiException;
    List<ApplicationSimpleDto> getTaskList(User principal, String query) throws ApiException;
    ApplicationDto findBy(Long id, User principal) throws ApiException;
    Application findOne(Long id);
    ApplicationDto create(ApplicationRequest request) throws ApiException;
    ApplicationDto update(Long id, ApplicationEditRequest request) throws ApiException;
    ApplicationDto delete(Long id, String comment) throws ApiException;
    boolean performAction(ActionRequest request, Long id, User principal) throws ApiException;
    List<Application> findAllByCurrentState(State state, Date startDate,Date endDate) throws ApiException;
}
