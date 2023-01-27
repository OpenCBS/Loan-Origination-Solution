package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.BaseRequest;
import com.opencbs.genesis.dto.requests.ResetUserPasswordByTokenRequest;
import com.opencbs.genesis.exceptions.ValidationException;

/**
 * Created by alopatin on 17-May-17.
 */
public interface ResetPasswordTokenService {
    String generateToken(BaseRequest request);
    void validateToken(BaseRequest request) throws ValidationException;
    void resetPasswordByToken(User currentUser, ResetUserPasswordByTokenRequest request) throws ValidationException;
}
