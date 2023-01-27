package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.EmailTemplate;
import com.opencbs.genesis.domain.enums.EmailTemplateType;

/**
 * Created by alopatin on 03-May-17.
 */
public interface EmailTemplateService {
    EmailTemplate findOneByType(EmailTemplateType type);
}
