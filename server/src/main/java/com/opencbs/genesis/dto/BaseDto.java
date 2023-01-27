package com.opencbs.genesis.dto;

import com.opencbs.genesis.dto.requests.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BaseDto implements BaseRequestDto {

    private long id;

}
