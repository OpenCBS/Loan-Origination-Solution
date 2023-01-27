package com.opencbs.genesis.controllers;

import com.opencbs.genesis.dto.responses.ApiResponse;

/**
 * Created by Makhsut Islamov on 24.10.2016.
 */

public abstract class BaseController {
    protected <T> ApiResponse<T> ReturnResponse(T data){
        return new ApiResponse<>(data);
    }
}
