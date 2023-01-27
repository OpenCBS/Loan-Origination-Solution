package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Role;

/**
 * Created by Makhsut Islamov on 03.11.2016.
 */
public interface RoleRepository extends Repository<Role>{
    Role findOneByCode(String code);
}
