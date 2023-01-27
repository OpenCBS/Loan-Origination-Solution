package com.opencbs.genesis.exceptions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundExceptionDto {
    private Integer status;
    private String message;
}
