package com.opencbs.genesis.request.profilefieldrequest;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileFieldValueCreateRequest {
    private final String sectionCode;
    private final Profile owner;
    private final ProfileField profileField;
    private final String value;
}
