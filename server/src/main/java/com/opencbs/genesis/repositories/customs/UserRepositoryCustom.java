package com.opencbs.genesis.repositories.customs;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.dto.PermissionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by alopatin on 12-Apr-17.
 */
public interface UserRepositoryCustom {
    List<User> findAll(List<PermissionDto> permissions);
    Page<User> findBy(String query, Pageable pageable);
}
