package com.opencbs.genesis.dto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alopatin on 13-Apr-17.
 */
public class StateSimpleDto extends SimpleInfoDto {

    public StateSimpleDto(){ }
    public StateSimpleDto(Long id, String name, String description, List<PermissionDto> permissions) {
        super(id, name, description);
        this.permissions=permissions;
        setResponsibleUsers();

    }

    private List<PermissionDto> permissions;
    private String responsibleUsers;

    public List<PermissionDto> getPermissions(){ return  permissions;}
    public void setPermissions(List<PermissionDto> permissions){
        this.permissions = permissions;
        setResponsibleUsers();}

    public void setResponsibleUsers() {
        responsibleUsers = "";
        if (getPermissions() != null && getPermissions().size() != 0) {
            responsibleUsers = String.join(",", getPermissions().stream().map(PermissionDto::getName).collect(Collectors.toList()));
        }
    }

    public String getResponsibleUsers() { return  responsibleUsers;}
}
