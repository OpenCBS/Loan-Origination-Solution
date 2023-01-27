package com.opencbs.genesis.domain.enums;

/**
 * Created by Makhsut Islamov on 15.12.2016.
 */
public enum SystemPermissions {
    ALL("All permissions"),
    READ_PROFILE("Read individual/list profiles"),
    CREATE_PROFILE("Create profile"),
    EDIT_PROFILE("Edit profile"),
    DELETE_PROFILE("Delete profile"),

    READ_APPLICATION("Read individual/list applications"),
    CREATE_APPLICATION("Create application"),
    EDIT_APPLICATION("Change application's state"),
    DELETE_APPLICATION("Delete application"),

    READ_APPLICATION_FIELDS("Read all application's fields"),
    EDIT_APPLICATION_FIELDS("Edit all application's fields"),

    READ_APPLICATION_PERMISSIONS("Read application's permissions"),
    EDIT_APPLICATION_PERMISSIONS("Edit application's permissions"),

    READ_APPLICATION_ATTACHMENTS("Read application's attachments"),
    CREATE_APPLICATION_ATTACHMENTS("Create application's attachments"),
    DELETE_APPLICATION_ATTACHMENTS("Delete application's attachments"),

    READ_APPLICATION_WORKLOGS("Read application's work logs"),
    CREATE_APPLICATION_WORKLOGS("Create application's work logs"),
    EDIT_APPLICATION_WORKLOGS("Edit application's work logs"),

    READ_USER("Read individual/list users"),
    CREATE_USER("Create user"),
    EDIT_USER("Edit user"),
    RESET_USER_PASSWORD("Reset users passwords"),

    READ_ROLE("Read individual/list roles"),
    CREATE_ROLE("Create role"),
    EDIT_ROLE("Edit role"),

    READ_ALERTS("Read alerts")

    ;

    private final String description;

    SystemPermissions(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}