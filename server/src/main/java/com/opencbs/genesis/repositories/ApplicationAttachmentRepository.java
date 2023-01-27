package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.ApplicationAttachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
public interface ApplicationAttachmentRepository extends Repository<ApplicationAttachment> {
    Page<ApplicationAttachment> findByApplicationId(Long applicationId, Pageable pageable);
    ApplicationAttachment findOneByNameAndApplicationId(String name, Long applicationId);
    List<ApplicationAttachment> findAllByApplicationIdAndCreatedDateGreaterThanAndCreatedDateLessThan(Long applicationId, Date startDate, Date endDate);
}
