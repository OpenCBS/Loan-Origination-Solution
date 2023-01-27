package com.opencbs.genesis.security;


import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.exceptions.ForbiddenException;
import com.opencbs.genesis.exceptions.UnauthorizedException;
import com.opencbs.genesis.helpers.ListHelper;
import com.opencbs.genesis.helpers.SystemPermissionHelper;
import com.opencbs.genesis.helpers.TokenHelper;
import com.opencbs.genesis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
        if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
        }else {
            try {
                final User user = userService.findByUsername(TokenHelper.extractUsername(req));
                initPrincipal(req, user);
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(user));
                processPermission(user, o);
            } catch (UnauthorizedException | ForbiddenException ex) {
                if (!isAnonymousAllowed(o))
                    throw ex;
            }
        }

        return super.preHandle(req, res, o);
    }

    private void initPrincipal(HttpServletRequest req, User user){
        User principal = (User) req.getUserPrincipal();

        principal.setId(user.getId());
        principal.setUsername(user.getUsername());
        principal.setPassword(user.getPassword());
        principal.setRole(user.getRole());
        principal.setFirstName(user.getFirstName());
        principal.setLastName(user.getLastName());
        principal.setEmail(user.getEmail());
        principal.setMobilePhone(user.getMobilePhone());
        principal.setPhoneNumber(user.getPhoneNumber());
        principal.setCurrentOccupation(user.getCurrentOccupation());
        principal.setSpokenLanguages(user.getSpokenLanguages());
        principal.setSpecialization(user.getSpecialization());
        principal.setAvailability(user.getAvailability());
        principal.setAlreadyVolunteered(user.getAlreadyVolunteered());
        principal.setSupportOther(user.getSupportOther());
        principal.setSupport(user.getSupport());
        principal.setCitizenship(user.getCitizenship());
        principal.setSupportPromotersOther(user.getSupportPromotersOther());
        principal.setSupportPromoters(user.getSupportPromoters());
        principal.setAddress(user.getAddress());
        principal.setPreferredWorkingLanguages(user.getPreferredWorkingLanguages());
        principal.setStreet(user.getStreet());
        principal.setPostalCode(user.getPostalCode());
        principal.setPhotoPath(user.getPhotoPath());
        principal.setPhotoContentType(user.getPhotoContentType());
        principal.setPhotoName(user.getPhotoName());
    }


    private void processPermission(User user, Object o) throws ForbiddenException {
        Method method = ((HandlerMethod) o).getMethod();
        if (!method.isAnnotationPresent(RequiresPermission.class)) {
            // No permission declared, thus proceed with execution.
            return;
        }

        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);

        if (!SystemPermissionHelper.hasPermission(user, requiresPermission.value())) {
            throw new ForbiddenException();
        }
    }

    private boolean isAnonymousAllowed(Object o){
        Method method = ((HandlerMethod) o).getMethod();
        return method.isAnnotationPresent(AllowAnonymous.class);
    }
}