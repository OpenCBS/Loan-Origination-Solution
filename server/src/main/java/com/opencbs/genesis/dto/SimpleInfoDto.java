package com.opencbs.genesis.dto;

/**
 * Created by Makhsut Islamov on 28.10.2016.
 */
public class SimpleInfoDto extends IdDto{
    private String name;
    private String description;

    public SimpleInfoDto(){}
    public SimpleInfoDto(Long id, String name){
        super(id);
        this.name = name;
    }

    public SimpleInfoDto(Long id, String name, String description){
        this(id, name);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
