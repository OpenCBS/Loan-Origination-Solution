package com.opencbs.genesis.dto;

import lombok.Data;

/**
 * Created by alopatin on 19-Apr-17.
 */
@Data
public class EventParticipantsDto extends IdDto {
    private String name;
    private Boolean isUser;
    private String email;
    private Long applicationId;

    public EventParticipantsDto(){}
    public EventParticipantsDto(Long id, String name, String email, boolean isUser){
        super(id);

        setName(name);
        setIsUser(isUser);
        setEmail(email);
    }
}
