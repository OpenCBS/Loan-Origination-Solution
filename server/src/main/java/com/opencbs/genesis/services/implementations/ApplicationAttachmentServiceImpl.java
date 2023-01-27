package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Application;
import com.opencbs.genesis.domain.ApplicationAttachment;
import com.opencbs.genesis.exceptions.ApiException;
import com.opencbs.genesis.helpers.FileHelper;
import com.opencbs.genesis.properties.AttachmentProperties;
import com.opencbs.genesis.repositories.ApplicationAttachmentRepository;
import com.opencbs.genesis.services.ApplicationAttachmentService;
import com.opencbs.genesis.services.ApplicationService;
import com.opencbs.genesis.services.EmailNotificationService;
import com.opencbs.genesis.validators.ApplicationAttachmentValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@Service
public class ApplicationAttachmentServiceImpl extends BaseService implements ApplicationAttachmentService {
    private final ApplicationAttachmentRepository applicationAttachmentRepository;
    private final Path rootLocation;
    private final EmailNotificationService emailNotificationService;
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationAttachmentServiceImpl(ApplicationAttachmentRepository applicationAttachmentRepository,
                                            AttachmentProperties attachmentProperties,
                                            EmailNotificationService emailNotificationService,
                                            ApplicationService applicationService) {
        this.applicationAttachmentRepository = applicationAttachmentRepository;
        this.rootLocation = Paths.get(attachmentProperties.getLocation());
        this.emailNotificationService = emailNotificationService;
        this.applicationService = applicationService;
    }

    @Override
    public Page<ApplicationAttachment> findBy(Long applicationId, Pageable pageable) {
        return applicationAttachmentRepository.findByApplicationId(applicationId, pageable);
    }

    @Override
    @Transactional
    @ValidateWith(ApplicationAttachmentValidator.class)
    public ApplicationAttachment create(Long applicationId, MultipartFile file) throws ApiException{
        Path savedPath = this.rootLocation.resolve(String.format("%s_%s", UUID.randomUUID(), file.getOriginalFilename()));
        FileHelper.save(file, savedPath);

        Application application = this.applicationService.findOne(applicationId);

        ApplicationAttachment attachment = new ApplicationAttachment();
        attachment.setApplication(application);
        attachment.setName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        attachment.setPath(savedPath.toString());

        attachment = applicationAttachmentRepository.save(attachment);

        this.emailNotificationService.attachmentAdded(attachment);
        return attachment;
    }

    @Override
    public Resource findAttachment(Long applicationId, String fileName) throws ApiException {
        ApplicationAttachment attachment = applicationAttachmentRepository.findOneByNameAndApplicationId(fileName, applicationId);
        Assert.notNull(attachment, "Requested file not found.");

        return FileHelper.loadAsResource(Paths.get(attachment.getPath()));
    }

    @Override
    @Transactional
    public ApplicationAttachment delete(Long id) throws ApiException {
        ApplicationAttachment attachment = applicationAttachmentRepository.findOne(id);
        Assert.notNull(attachment, "Attachment not found!");

        FileHelper.delete(Paths.get(attachment.getPath()));
        applicationAttachmentRepository.delete(attachment);
        return attachment;
    }

    @Override
    public List<ApplicationAttachment> findByDateRange(Long applicationId, Date startDate, Date endDate) {
        return applicationAttachmentRepository.findAllByApplicationIdAndCreatedDateGreaterThanAndCreatedDateLessThan(applicationId,startDate,endDate);
    }
}