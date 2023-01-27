package com.opencbs.genesis.dto;

import com.opencbs.genesis.domain.WorkflowField;

/**
 * Created by Makhsut Islamov on 08.12.2016.
 */
public class FieldDto {
    private WorkflowField field;
    private boolean readOnly;
    private String value;

    public FieldDto(){}
    public FieldDto(WorkflowField field, String value, boolean readOnly){
        this.field = field;
        this.value = value;
        this.readOnly = readOnly;
    }

    public WorkflowField getField() {
        return field;
    }

    public void setField(WorkflowField field) {
        this.field = field;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
