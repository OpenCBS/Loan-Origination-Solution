package com.opencbs.genesis.dto;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

/**
 * Created by alopatin on 22-May-17.
 */
public class UserPhotoDto {
    private Resource resource;
    private MediaType mediaType;
    private String name;

    public MediaType  getMediaType(){return this.mediaType;}
    public void setMediaType(MediaType mediaType){this.mediaType =mediaType;}

    public Resource getResource(){return  this.resource;}
    public void setResource(Resource resource){ this.resource = resource;}

    public String getName(){return this.name;}
    public void  setName(String name){ this.name = name;}
}
