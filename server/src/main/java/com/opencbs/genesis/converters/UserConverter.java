package com.opencbs.genesis.converters;

import com.opencbs.genesis.domain.Role;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.requests.UpdateUserRequest;
import com.opencbs.genesis.dto.requests.UserRequest;

/**
 * Created by alopatin on 24-Apr-17.
 */
public class UserConverter {
    public static User toEntity(UserRequest request, User user){
        user.setUsername(request.getUsername());
        user.setRole(new Role(request.getRoleId()));
        user.setEnabled(request.getEnabled());

        return toEntity((UpdateUserRequest)request,user);
    }
    public static User toEntity(UpdateUserRequest request, User user){

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobilePhone(request.getMobilePhone());


        user.setAlreadyVolunteered(request.getAlreadyVolunteered());
        user.setAvailability(request.getAvailability());
        user.setCitizenship(request.getCitizenship());
        user.setCurrentOccupation(request.getCurrentOccupation());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSpecialization(request.getSpecialization());
        user.setSpokenLanguages(request.getSpokenLanguages());
        user.setSupportPromoters(request.getSupportPromoters());
        user.setSupportPromotersOther(request.getSupportPromotersOther());
        user.setSupport(request.getSupport());
        user.setSupportOther(request.getSupportOther());
        user.setAddress(request.getAddress());
        user.setPreferredWorkingLanguages(request.getPreferredWorkingLanguages());
        user.setStreet(request.getStreet());
        user.setPostalCode(request.getPostalCode());

        return user;
    }



    public static User toEntity(User request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobilePhone(request.getMobilePhone());
        user.setRole(request.getRole());
        user.setEnabled(request.isEnabled());

        user.setAlreadyVolunteered(request.getAlreadyVolunteered());
        user.setAvailability(request.getAvailability());
        user.setCitizenship(request.getCitizenship());
        user.setCurrentOccupation(request.getCurrentOccupation());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSpecialization(request.getSpecialization());
        user.setSpokenLanguages(request.getSpokenLanguages());
        user.setSupportPromoters(request.getSupportPromoters());
        user.setSupportPromotersOther(request.getSupportPromotersOther());
        user.setSupport(request.getSupport());
        user.setSupportOther(request.getSupportOther());
        user.setAddress(request.getAddress());
        user.setPreferredWorkingLanguages(request.getPreferredWorkingLanguages());
        user.setStreet(request.getStreet());
        user.setPostalCode(request.getPostalCode());

        return user;
    }
}
