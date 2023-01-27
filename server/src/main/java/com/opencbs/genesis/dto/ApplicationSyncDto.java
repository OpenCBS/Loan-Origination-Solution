package com.opencbs.genesis.dto;

import com.opencbs.genesis.domain.ApplicationFieldValue;

import java.util.List;

/**
 * Created by Makhsut Islamov on 30.03.2017.
 */
public class ApplicationSyncDto extends ApplicationSimpleDto {
    private List<ApplicationFieldValue> fieldValues;
    private String userName;

    public String getUserName(){ return this.userName;}
    public void setUserName(String userName){ this.userName =userName;}

    public List<ApplicationFieldValue> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<ApplicationFieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
