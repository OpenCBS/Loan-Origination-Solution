package com.opencbs.genesis.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.security.Principal;

/**
 * Created by Makhsut Islamov on 26.10.2016.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Principal principal;

    public JwtAuthenticationToken(Principal principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
