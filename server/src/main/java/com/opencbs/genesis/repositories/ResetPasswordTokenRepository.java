package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.ResetUserPasswordToken;
import com.opencbs.genesis.domain.User;

/**
 * Created by alopatin on 17-May-17.
 */
public interface ResetPasswordTokenRepository extends Repository<ResetUserPasswordToken> {
    ResetUserPasswordToken findByUser(User user);
    ResetUserPasswordToken findOneByToken(String data);
}
