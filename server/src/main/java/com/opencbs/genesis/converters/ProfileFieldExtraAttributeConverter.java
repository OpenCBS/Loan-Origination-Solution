package com.opencbs.genesis.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencbs.genesis.domain.ProfileFieldExtra;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ProfileFieldExtraAttributeConverter implements AttributeConverter<ProfileFieldExtra, String> {
    @Override
    public String convertToDatabaseColumn(ProfileFieldExtra extra) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(extra);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public ProfileFieldExtra convertToEntityAttribute(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(s, ProfileFieldExtra.class);
        } catch (IOException e) {
            return null;
        }
    }
}
