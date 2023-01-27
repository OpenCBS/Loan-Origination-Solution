package com.opencbs.genesis.domain.events;

import com.opencbs.genesis.domain.User;

import javax.persistence.*;

/**
 * Created by alopatin on 19-Apr-17.
 */

@Entity
@DiscriminatorValue("User")
public class UserEventParticipants extends EventParticipants {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id", nullable = false)
    private User user;

    public User getUser() {return  this.user;}
    public void setUser(User user){ this.user = user;}
}
