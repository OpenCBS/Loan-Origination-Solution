package com.opencbs.genesis.dto;

import lombok.Data;

/**
 * Created by Makhsut Islamov on 28.10.2016.
 */
@Data
public class IdDto {
    private Long id;

    public IdDto(){}
    public IdDto(Long id){
        this.id = id;
    }
}
