package com.opencbs.genesis.domain.permissions;

import com.opencbs.genesis.domain.User;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
@Entity
@DiscriminatorValue("User")
public class UserWorkflowStatePermission extends WorkflowStatePermission {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
