package com.opencbs.genesis.dto;

import com.opencbs.genesis.domain.User;

import java.util.Date;

/**
 * Created by Makhsut Islamov on 26.12.2016.
 */
public class ApplicationLogDto {
    private String fromStateName;
    private String toStateName;
    private String note;
    private Date createDate;
    private User createdUser;

    public String getFromStateName() {
        return fromStateName;
    }

    public void setFromStateName(String fromStateName) {
        this.fromStateName = fromStateName;
    }

    public String getToStateName() {
        return toStateName;
    }

    public void setToStateName(String toStateName) {
        this.toStateName = toStateName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }
}