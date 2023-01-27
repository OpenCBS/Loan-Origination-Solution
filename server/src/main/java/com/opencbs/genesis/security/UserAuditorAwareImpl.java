package com.opencbs.genesis.security;

import com.opencbs.genesis.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Makhsut Islamov on 26.10.2016.
 */
public class UserAuditorAwareImpl implements AuditorAware<User> {
    @Override
    public User getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

       return (User)authentication.getPrincipal();
    }
}
