package com.opencbs.genesis.dto;

/**
 * Created by Makhsut Islamov on 16.12.2016.
 */
public class CodeNameDto {
    private String code;
    private String name;

    public CodeNameDto(){}
    public CodeNameDto(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
