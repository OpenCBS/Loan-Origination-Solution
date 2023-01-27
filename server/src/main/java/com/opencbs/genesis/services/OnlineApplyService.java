package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.OnlineApplyDto;
import com.opencbs.genesis.exceptions.ApiException;

public interface OnlineApplyService {
    void create(OnlineApplyDto applyDto, User currentUser) throws ApiException;
}
