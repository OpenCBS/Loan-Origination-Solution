package com.opencbs.genesis.domain;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alopatin on 17-May-17.
 */

@Entity
@Table(name = "reset_user_password_token")
@EntityListeners(AuditingEntityListener.class)
public class ResetUserPasswordToken extends BaseEntity {
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "expire_date", nullable = false)
    private Date expireDate;

    @Type(type = "com.opencbs.genesis.domain.types.UTCDateType")
    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Date createDate;


    public String getToken(){return  this.token;}
    public void setToken(String token) { this.token = token;}

    public User getUser(){ return  this.user;}
    public void setUser(User user){ this.user = user;}

    public Date getExpireDate(){return  this.expireDate;}
    public void setExpireDate(Date expireDate){ this.expireDate = expireDate;}

    public Date getCreateDate(){  return  this.createDate;}
    public void setCreateDate(Date createDate){    this.createDate = createDate;}
}
