package com.opencbs.genesis.dto.requests;

import com.opencbs.genesis.dto.StatePermissionDto;
import lombok.Data;

import java.util.List;

/**
 * Created by Makhsut Islamov on 28.10.2016.
 */
@Data
public class ApplicationRequest extends ApplicationEditRequest {
    private Long workflowId;
    private Long profileId;
    private Long responsibleUserId;
    private List<StatePermissionDto> statePermissions;
}