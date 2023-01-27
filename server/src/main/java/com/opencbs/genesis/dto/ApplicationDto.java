package com.opencbs.genesis.dto;

import java.util.List;

/**
 * Created by Makhsut Islamov on 26.10.2016.
 */
public class ApplicationDto extends ApplicationSimpleDto{
    private List<SimpleInfoDto> actions;

    public ApplicationDto() {
    }

    public List<SimpleInfoDto> getActions() {
        return actions;
    }

    public void setActions(List<SimpleInfoDto> actions) {
        this.actions = actions;
    }
}
