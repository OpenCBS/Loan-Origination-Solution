package com.opencbs.genesis.security;

import com.opencbs.genesis.domain.enums.SystemPermissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermission {
    SystemPermissions value();
}