package com.opencbs.genesis.dto;

import com.opencbs.genesis.domain.Profile;

import java.util.Date;

/**
 * Created by Makhsut Islamov on 28.10.2016.
 */
public class ApplicationSimpleDto extends IdDto{
    private String name;
    private SimpleInfoDto workflowInfo;
    private Profile profile;
    private StateSimpleDto currentState;
    private SimpleInfoDto createdUser;
    private SimpleInfoDto responsibleUser;
    private Date createdDate;
    private Date statusChangedAt;
    private boolean completed;
    private boolean isDeleted;
    private boolean deletable;
    private String comment;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public StateSimpleDto getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateSimpleDto currentState) {
        this.currentState = currentState;
    }

    public SimpleInfoDto getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(SimpleInfoDto createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public SimpleInfoDto getWorkflowInfo() {
        return workflowInfo;
    }

    public void setWorkflowInfo(SimpleInfoDto workflowInfo) {
        this.workflowInfo = workflowInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleInfoDto getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(SimpleInfoDto responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public Date getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(Date statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}