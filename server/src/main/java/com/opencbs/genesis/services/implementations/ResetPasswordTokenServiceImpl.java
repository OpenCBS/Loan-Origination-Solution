package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.ResetUserPasswordToken;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.BaseRequest;
import com.opencbs.genesis.dto.requests.ResetUserPasswordByTokenRequest;
import com.opencbs.genesis.dto.requests.ResetUserPasswordRequest;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.helpers.DateHelper;
import com.opencbs.genesis.properties.GeneralProperties;
import com.opencbs.genesis.repositories.ResetPasswordTokenRepository;
import com.opencbs.genesis.services.EmailNotificationService;
import com.opencbs.genesis.services.ResetPasswordTokenService;
import com.opencbs.genesis.services.UserService;
import com.opencbs.genesis.validators.CreateResetPasswordTokenRequestValidator;
import com.opencbs.genesis.validators.ResetPasswordByTokenRequestValidator;
import com.opencbs.genesis.validators.ValidateResetPasswordTokenRequestValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by alopatin on 17-May-17.
 */

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final UserService userService;
    private final EmailNotificationService notificationService;
    private final GeneralProperties generalProperties;

    @Autowired
    ResetPasswordTokenServiceImpl(ResetPasswordTokenRepository resetPasswordTokenRepository,
                                  UserService userService,
                                  EmailNotificationService notificationService,
                                  GeneralProperties generalProperties){
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.generalProperties = generalProperties;
    }

    @Override
    @Transactional
    @ValidateWith(CreateResetPasswordTokenRequestValidator.class)
    public String generateToken(BaseRequest request) {
        User user = this.userService.findByEmailAndEnabledTrue(request.getData());

        String token =generateTokenString();
        ResetUserPasswordToken tokenRequest = this.resetPasswordTokenRepository.findByUser(user);
        if (tokenRequest == null) {
            tokenRequest = new ResetUserPasswordToken();
            tokenRequest.setId(null);
            tokenRequest.setUser(user);
        }else{
            tokenRequest.setCreateDate(new Date());
        }

        tokenRequest.setToken(token);
        tokenRequest.setExpireDate(DateHelper.addMinutes(new Date(),Integer.parseInt(generalProperties.getTokenValidMinutes())));
        this.resetPasswordTokenRepository.save(tokenRequest);

        this.notificationService.sendTokenForResetPassword(user,token);
        return token;
    }

    private String generateTokenString(){
        return  UUID.randomUUID().toString() + UUID.randomUUID().toString()+UUID.randomUUID().toString();
    }

    @Override
    @Transactional
    @ValidateWith(ValidateResetPasswordTokenRequestValidator.class)
    public void validateToken(BaseRequest request) throws ValidationException {
        ResetUserPasswordToken token = this.resetPasswordTokenRepository.findOneByToken(request.getData());
        if (!isTokenNotExpired((token))) {
            this.resetPasswordTokenRepository.delete(token.getId());
            throw new ValidationException("token has expired");
        }
    }

    @Override
    @Transactional
    @ValidateWith(ResetPasswordByTokenRequestValidator.class)
    public void resetPasswordByToken(User currentUser,ResetUserPasswordByTokenRequest request) throws ValidationException {
        ResetUserPasswordToken token = this.resetPasswordTokenRepository.findOneByToken(request.getData());
        if (!isTokenNotExpired((token))) {
            this.resetPasswordTokenRepository.delete(token.getId());
            throw new ValidationException("token has expired");
        }

        ResetUserPasswordRequest resetPasswordRequest = new ResetUserPasswordRequest();
        resetPasswordRequest.setNewPassword(request.getNewPassword());
        resetPasswordRequest.setUserId(token.getUser().getId());

         this.userService.resetUserPassword(currentUser,resetPasswordRequest);
         this.resetPasswordTokenRepository.delete(token);
    }

    private boolean isTokenNotExpired(ResetUserPasswordToken token){
        Date currentDate = new Date();
        return currentDate.before(token.getExpireDate());
    }
}
