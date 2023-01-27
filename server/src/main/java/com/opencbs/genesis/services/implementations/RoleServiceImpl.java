package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Role;
import com.opencbs.genesis.exceptions.ValidationException;
import com.opencbs.genesis.repositories.RoleRepository;
import com.opencbs.genesis.services.RoleService;
import com.opencbs.genesis.validators.RoleDeleteRequestValidator;
import com.opencbs.genesis.validators.RoleValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Makhsut Islamov on 03.11.2016.
 */
@Service
public class RoleServiceImpl extends BaseService implements RoleService{
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role findById(Long id) {
        Role role = roleRepository.findOne(id);
        Assert.notNull(role, String.format("Role with {id : %d} not found.", id));
        return role;
    }

    @Override
    @ValidateWith(RoleValidator.class)
    @Transactional
    public Role create(Role role) throws ValidationException {
        role.setId(0L);
        return roleRepository.save(role);
    }

    @Override
    @ValidateWith(RoleValidator.class)
    @Transactional
    public Role update(Role role, Long id) throws ValidationException {
        role.setId(id);
        return roleRepository.save(role);
    }

    @Override
    @ValidateWith(RoleDeleteRequestValidator.class)
    @Transactional
    public Role delete(Long id) {
        Role role = roleRepository.findOne(id);
        roleRepository.delete(role);
        return role;
    }
}