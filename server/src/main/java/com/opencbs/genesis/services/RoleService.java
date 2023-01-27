package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Role;
import com.opencbs.genesis.exceptions.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Makhsut Islamov on 03.11.2016.
 */
public interface RoleService {
    Page<Role> findAll(Pageable pageable);
    Role findById(Long id);
    Role create(Role role) throws ApiException;
    Role update(Role role, Long id) throws ApiException;
    Role delete(Long id) throws ApiException;
}
