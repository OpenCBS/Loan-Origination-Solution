package com.opencbs.genesis.aspects;

import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.validators.Validator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Created by Makhsut Islamov on 07.11.2016.
 */
@Aspect
public class ServiceValidationAspect {
    @Autowired
    private ApplicationContext context;

    @SuppressWarnings("unchecked")
    @Before(value = "execution(* com.opencbs.genesis.services.implementations..*.*(..)) && @annotation(validateWith)")
    public void validate(JoinPoint joinPoint, ValidateWith validateWith) throws ApiException {
        Object[] args = joinPoint.getArgs();
        Class validatorClass = validateWith.value();

        Validator validator = getValidator(validatorClass);
        validator.validate(args);
    }

    @SuppressWarnings("unchecked")
    private Validator getValidator(Class clazz) throws ValidationException {
        Map<String, Validator> beans = context.getBeansOfType(clazz);
        if(beans.isEmpty()) {
            throw new ValidationException(String.format("Bean for '%s' validator not found!", clazz.getName()));
        }

        return (Validator)beans.values().toArray()[0];
    }
}