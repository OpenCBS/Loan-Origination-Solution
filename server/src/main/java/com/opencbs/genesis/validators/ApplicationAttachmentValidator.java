package com.opencbs.genesis.validators;

import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.ApplicationAttachmentRepository;
import com.opencbs.genesis.repositories.ApplicationRepository;
import com.opencbs.genesis.validators.helpers.ParamsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@Component
public class ApplicationAttachmentValidator extends BaseValidator {
    private final ApplicationRepository applicationRepository;
    private final ApplicationAttachmentRepository applicationAttachmentRepository;

    @Autowired
    public ApplicationAttachmentValidator(ApplicationRepository applicationRepository,
                                          ApplicationAttachmentRepository applicationAttachmentRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationAttachmentRepository = applicationAttachmentRepository;
    }

    @Override
    protected void validateInternal(Object... params) throws ValidationException {
        Long applicationId = ParamsHelper.getAs(params, 0);
        MultipartFile file = ParamsHelper.getAs(params, 1);

        Assert.notNull(file, "file can not be null");
        Assert.isTrue(!file.isEmpty(), "Failed to store empty file " + file.getOriginalFilename());

        Assert.isTrue(applicationRepository.exists(applicationId), "Application not found.");

        Assert.isNull(applicationAttachmentRepository.findOneByNameAndApplicationId(file.getOriginalFilename(), applicationId),
                String.format("The '%s' already exists.", file.getOriginalFilename()));
    }
}
