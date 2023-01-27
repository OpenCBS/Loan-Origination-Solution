package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.EmailTemplate;
import com.opencbs.genesis.domain.enums.EmailTemplateType;

/**
 * Created by alopatin on 03-May-17.
 */
public interface EmailTemplateRepository extends Repository<EmailTemplate> {
    EmailTemplate findByType(EmailTemplateType type);
}
