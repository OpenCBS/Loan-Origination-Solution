package com.opencbs.genesis.domain.permissions;

import com.opencbs.genesis.domain.Role;

import javax.persistence.*;

/**
 * Created by Makhsut Islamov on 27.10.2016.
 */
@Entity
@DiscriminatorValue("Role")
public class RoleWorkflowStatePermission extends WorkflowStatePermission {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id", nullable = false)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
