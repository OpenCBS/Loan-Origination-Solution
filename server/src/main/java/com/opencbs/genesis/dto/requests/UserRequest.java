package com.opencbs.genesis.dto.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Makhsut Islamov on 04.11.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends UpdateUserRequest {
    private String username;
    private String password;
    private Long roleId;
    private Boolean enabled;
}
