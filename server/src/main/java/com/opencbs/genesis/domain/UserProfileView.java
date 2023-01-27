package com.opencbs.genesis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alopatin on 19-Apr-17.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "user_profile_views")
public class UserProfileView extends BaseUserProfileEntity {
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "is_user", nullable = false)
    private boolean isUser;

    @Column(name = "full_name", nullable = false)
    private String fullName;
}
