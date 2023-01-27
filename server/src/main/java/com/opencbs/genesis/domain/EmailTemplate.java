package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.enums.EmailTemplateType;

import javax.persistence.*;

/**
 * Created by alopatin on 03-May-17.
 */

@Entity
@Table(name = "email_templates")
public class EmailTemplate extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EmailTemplateType type;

    public String getName(){ return this.name;}
    public void setName(String name){ this.name = name;}

    public String getTitle(){ return this.title;}
    public void setTitle(String title){ this.title = title;}

    public String getContent(){ return this.content;}
    public void setContent(String content){ this.content = content;}

    public EmailTemplateType getType(){ return this.type;}
    public void setType(EmailTemplateType type){ this.type = type;}
}
