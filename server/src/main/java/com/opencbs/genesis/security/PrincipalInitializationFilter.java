package com.opencbs.genesis.security;

import com.opencbs.genesis.domain.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class PrincipalInitializationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        chain.doFilter(new UserRoleRequestWrapper(new User(), request), res);
    }
}

