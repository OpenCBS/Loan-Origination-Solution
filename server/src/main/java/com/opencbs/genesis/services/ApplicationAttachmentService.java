package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.ApplicationAttachment;
import com.opencbs.genesis.exceptions.ApiException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
public interface ApplicationAttachmentService {
    Page<ApplicationAttachment> findBy(Long applicationId, Pageable pageable);
    ApplicationAttachment create(Long applicationId, MultipartFile file) throws ApiException;
    Resource findAttachment(Long applicationId, String fileName) throws ApiException;
    ApplicationAttachment delete(Long id) throws ApiException;
    List<ApplicationAttachment> findByDateRange(Long applicationId, Date startDate, Date endDate);
}
