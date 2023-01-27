package com.opencbs.genesis.request.profilefieldrequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileFieldValueUpdateRequest {
    private final Long id;
    private final String value;
}
