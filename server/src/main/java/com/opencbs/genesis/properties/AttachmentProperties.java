package com.opencbs.genesis.properties;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
@Data
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "attachment")
public class AttachmentProperties {
    @NotBlank(message = "Location for the attachments is not configured.")
    private String location;
}
