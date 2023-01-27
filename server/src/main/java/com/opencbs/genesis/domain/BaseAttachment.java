package com.opencbs.genesis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alopatin on 18-May-17.
 */

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseAttachment extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "path", nullable = false)
    @JsonIgnore
    private String path;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Date createdDate;





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


}
