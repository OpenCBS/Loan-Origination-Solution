package com.opencbs.genesis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by Makhsut Islamov on 22.03.2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FieldSectionDto extends IdDto {
    private String caption;
    private Integer order;
    private List<FieldDto> fields;

    public FieldSectionDto() {}
    public FieldSectionDto(Long id, String caption, Integer order, List<FieldDto> fields) {
        this.setId(id);
        this.caption = caption;
        this.order = order;
        this.fields = fields;
    }
}
