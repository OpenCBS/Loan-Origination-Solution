package com.opencbs.genesis.dto.requests;

import lombok.Data;

/**
 * Created by alopatin on 17-May-17.
 */
@Data
public class ResetUserPasswordRequest {
    private Long userId;
    private String newPassword;
}
