package com.opencbs.genesis.dto.profile;

import com.opencbs.genesis.dto.profile.field.ProfileFieldSectionDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProfileDto {
    private long id;
    private Date createdDate;
    private List<ProfileFieldSectionDto> profileFieldSections;
}
