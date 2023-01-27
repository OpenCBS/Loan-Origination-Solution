package com.opencbs.genesis.properties;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by alopatin on 13-Apr-17.
 */
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "general")
public class GeneralProperties {
    @NotBlank(message = "Site url is not configured.")
    private String siteUrl;

    @NotBlank(message = "Token valid minutes is not configured.")
    private String tokenValidMinutes;

    public String getSiteUrl(){return  siteUrl;}
    public void  setSiteUrl(String siteUrl){this.siteUrl=siteUrl;}

    public String getTokenValidMinutes(){return  this.tokenValidMinutes;}
    public void setTokenValidMinutes(String tokenValidMinutes){  this.tokenValidMinutes=tokenValidMinutes;}

}
