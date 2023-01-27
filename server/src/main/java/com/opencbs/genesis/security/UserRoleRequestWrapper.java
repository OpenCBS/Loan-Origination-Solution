package com.opencbs.genesis.security;

import com.opencbs.genesis.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    private User user;
    private HttpServletRequest realRequest;

    public UserRoleRequestWrapper(User user, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.realRequest = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (user.getRole() == null) {
            return this.realRequest.isUserInRole(role);
        }

        return role.equals(user.getRole().getCode());
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.user == null) {
            return realRequest.getUserPrincipal();
        }
        return user;
    }
}