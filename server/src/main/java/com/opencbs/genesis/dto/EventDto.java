package com.opencbs.genesis.dto;

import com.opencbs.genesis.dto.requests.EventRequest;
import lombok.Data;

import java.util.Date;

/**
 * Created by alopatin on 19-Apr-17.
 */
@Data
public class EventDto extends EventRequest {
    private Date createdDate;
    private SimpleInfoDto createdUser;
}
