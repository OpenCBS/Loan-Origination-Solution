package com.opencbs.genesis.dto.requests;

/**
 * Created by Makhsut Islamov on 28.10.2016.
 */
public class FieldValue {
    private Long fieldId;
    private String value;

    public FieldValue(){}

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
