package com.opencbs.genesis.exceptions.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FieldErrorDto {
    private final String field;
    private final List<String> messages;
}
