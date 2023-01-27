package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.exceptions.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


/**
 * Created by Makhsut Islamov on 24.10.2016.
 */
public interface ProfileService {
    Page<Profile> findAll(Pageable pageable);
    Page<Profile> findBy(String query, Pageable pageable);
    Optional<Profile> findById(Long id);
    Profile create(Profile newProfile) throws ApiException;
    Profile update(Profile profile, Long id) throws ApiException;
    void delete(Long id);

    Profile create();
}
