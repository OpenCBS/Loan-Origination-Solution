package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.repositories.customs.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Makhsut Islamov on 21.10.2016.
 */
public interface UserRepository extends Repository<User> ,UserRepositoryCustom {
    User findOneByUsernameAndEnabledTrue(String username);
    List<User> findAllByIdInAndEnabledTrue(List<Long> participantsAsUsers);
    Page<User> findByRoleId(Long roleId, Pageable pageable);
    List<User> findByRoleId(Long roleId);
    List<User> findUsersByRoleCode(String roleCode);

    User findOneByUsername(String username);

    @Query("select case when count(u) > 0 then true else false end from User u where u.role.id= ?1")
    boolean existsByRoleId(Long roleId);
    User findOneByEmailAndEnabledTrue(String email);
}
