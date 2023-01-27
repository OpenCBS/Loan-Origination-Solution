package com.opencbs.genesis.controllers;

import com.opencbs.genesis.dto.responses.ErrorResponse;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.exceptions.dto.EntityNotFoundExceptionDto;
import com.opencbs.genesis.exceptions.dto.FieldErrorDto;
import com.opencbs.genesis.exceptions.dto.ValidationExceptionDto;
import org.apache.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static Logger log = Logger.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ApiException ex) {
        ErrorResponse error = new ErrorResponse(ex.getHttpStatus().value(), ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(IllegalArgumentException ex) {
        ValidationException validationException = new ValidationException(ex.getMessage());
        return exceptionHandler(validationException);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(HttpStatusCodeException ex) {
        String errorMessage = String.format("Third-part error: [status : %s], [message: %s]", ex.getStatusCode(), ex.getResponseBodyAsString());
        ValidationException validationException = new ValidationException(errorMessage);
        return exceptionHandler(validationException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal_error",
                "Internal server error.");
        logError(ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EntityNotFoundExceptionDto> entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        EntityNotFoundExceptionDto entityNotFoundExceptionDto = new EntityNotFoundExceptionDto();
        entityNotFoundExceptionDto.setMessage(ex.getMessage());
        entityNotFoundExceptionDto.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(entityNotFoundExceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDto> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, List<FieldError>> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField));

        List<FieldErrorDto> fieldErrorDtos = new ArrayList<>();

        for (Map.Entry<String, List<FieldError>> entry : fieldErrors.entrySet()) {
            List<String> messages = entry.getValue().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            FieldErrorDto fieldErrorDto = new FieldErrorDto(entry.getKey(), messages);
            fieldErrorDtos.add(fieldErrorDto);
        }

        ValidationExceptionDto validationExceptionDto = new ValidationExceptionDto();
        validationExceptionDto.setFields(fieldErrorDtos);
        validationExceptionDto.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return new ResponseEntity<>(validationExceptionDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private void logError(Exception e) {
        log.error("-------------- ERROR --------------");
        log.error(" MESSAGE: " + e.getMessage());
        log.error("Stacktrace: ", e);
        log.error("-----------------------------------");
    }
}