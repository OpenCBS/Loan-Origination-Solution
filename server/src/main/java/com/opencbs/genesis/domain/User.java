package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.security.Principal;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
@Entity
@Table(name = "users")
public class User extends BaseUserProfileEntity implements Principal{
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "current_occupation")
    private String currentOccupation;

    @Column(name = "spoken_languages")
    private String spokenLanguages;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "availability")
    private String availability;

    @Column(name = "already_volunteered")
    private String alreadyVolunteered;

    @Column(name = "supportOther")
    private String supportOther;

    @Column(name = "support_promoters_other")
    private String supportPromotersOther;

    @Column(name = "citizenship")
    private String citizenship;

    @JoinColumn(name = "support")
    private String support;

    @Column(name = "support_promoters")
    private String supportPromoters;

    @Column(name = "address")
    private String address;

    @Column(name = "preferred_working_languages")
    private String preferredWorkingLanguages;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "photo_content_type")
    private String photoContentType;

    public User(){}

    public User(Long id){
        setId(id);
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {return this.phoneNumber;}
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}

    public String getCurrentOccupation() {return  this.currentOccupation;}
    public void setCurrentOccupation(String currentOccupation) { this.currentOccupation =currentOccupation;}

    public String getSpokenLanguages(){ return this.spokenLanguages;}
    public void setSpokenLanguages(String spokenLanguages){ this.spokenLanguages = spokenLanguages;}

    public String getSpecialization(){ return this.specialization;}
    public void setSpecialization(String specialization) {this.specialization = specialization;}

    public String getAvailability(){ return this.availability;}
    public void setAvailability(String availability) { this.availability = availability;}

    public String getAlreadyVolunteered() {return  this.alreadyVolunteered;}
    public void setAlreadyVolunteered(String alreadyVolunteered) { this.alreadyVolunteered = alreadyVolunteered;}

    public String getSupportOther() {return this.supportOther;}
    public void setSupportOther(String supportOther) {this.supportOther = supportOther;}

    public String getSupportPromotersOther(){ return  this.supportPromotersOther;}
    public void  setSupportPromotersOther(String supportPromotersOther) {this.supportPromotersOther = supportPromotersOther;}

    public String getSupport(){return this.support;}
    public void setSupport(String support){ this.support = support;}

    public String getSupportPromoters(){ return this.supportPromoters;}
    public void setSupportPromoters(String supportPromoters){ this.supportPromoters = supportPromoters;}

    public String getCitizenship(){ return this.citizenship;}
    public void setCitizenship(String citizenship){ this.citizenship = citizenship;}

    public String getAddress(){ return this.address;}
    public void setAddress(String address){ this.address = address;}

    public String getPreferredWorkingLanguages(){ return this.preferredWorkingLanguages;}
    public void setPreferredWorkingLanguages(String preferredWorkingLanguages){ this.preferredWorkingLanguages = preferredWorkingLanguages;}

    public String getStreet(){ return this.street;}
    public void setStreet(String street){ this.street = street;}

    public String getPostalCode(){ return this.postalCode;}
    public void setPostalCode(String postalCode){ this.postalCode = postalCode;}

    public String getPhotoName(){ return this.photoName;}
    public void setPhotoName(String photoName){ this.photoName = photoName;}

    public String getPhotoContentType(){ return this.photoContentType;}
    public void setPhotoContentType(String photoContentType){ this.photoContentType = photoContentType;}

    public String getPhotoPath(){ return this.photoPath;}
    public void setPhotoPath(String photoPath){ this.photoPath = photoPath;}


    @Override
    @JsonIgnore
    public String getName() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
