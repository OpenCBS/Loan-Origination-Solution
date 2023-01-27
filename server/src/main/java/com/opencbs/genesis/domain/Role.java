package com.opencbs.genesis.domain;

import com.opencbs.genesis.domain.enums.SystemPermissions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection(targetClass = SystemPermissions.class, fetch = FetchType.EAGER)
    @Cascade(value={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DELETE})
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission", nullable = false)
    private List<SystemPermissions> permissions;

    public Role(){}
    public Role(Long id){
        this.setId(id);
    }
}