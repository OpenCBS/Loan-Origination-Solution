package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.EmailTemplate;
import com.opencbs.genesis.domain.enums.EmailTemplateType;
import com.opencbs.genesis.repositories.EmailTemplateRepository;
import com.opencbs.genesis.services.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alopatin on 03-May-17.
 */

@Service
public class EmailTemplateServiceImpl extends BaseService implements EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;

    @Autowired
    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository){
        this.emailTemplateRepository = emailTemplateRepository;
    }

    @Override
    public EmailTemplate findOneByType(EmailTemplateType type) {
        return this.emailTemplateRepository.findByType(type);
    }
}
