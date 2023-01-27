package com.opencbs.genesis.dto.requests;

/**
 * Created by alopatin on 24-Apr-17.
 */
public class UpdateUserPasswordRequest {
    private String newPassword;
    private String currentPassword;

    public String getNewPassword(){ return this.newPassword;}
    public void setNewPassword(String newPassword) {this.newPassword = newPassword;}

    public String getCurrentPassword(){ return this.currentPassword;}
    public void setCurrentPassword(String currentPassword){ this.currentPassword = currentPassword;}
}
