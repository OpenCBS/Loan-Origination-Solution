package com.opencbs.genesis.exceptions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationExceptionDto {
    private Integer status;
    private List<FieldErrorDto> fields;
}
