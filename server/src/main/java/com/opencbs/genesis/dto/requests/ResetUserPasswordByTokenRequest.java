package com.opencbs.genesis.dto.requests;

/**
 * Created by alopatin on 18-May-17.
 */
public class ResetUserPasswordByTokenRequest extends BaseRequest {
    private String newPassword;


    public String getNewPassword() {return  this.newPassword;}
    public void setNewPassword(String newPassword) {this.newPassword= newPassword;}
}
