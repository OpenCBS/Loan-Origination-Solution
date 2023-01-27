package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.repositories.ProfileRepository;
import com.opencbs.genesis.services.ProfileService;
import com.opencbs.genesis.validators.ProfileDeleteRequestValidator;
import com.opencbs.genesis.validators.ProfileValidator;
import com.opencbs.genesis.validators.annotations.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by Makhsut Islamov on 24.10.2016.
 */
@Service
public class ProfileServiceImpl extends BaseService implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    public Page<Profile> findBy(String query, Pageable pageable) {
        return StringUtils.isEmpty(query) ? findAll(pageable) : profileRepository.findBy(query, pageable);
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return Optional.ofNullable(profileRepository.findOne(id));
    }

    @Override
    @ValidateWith(ProfileValidator.class)
    @Transactional
    public Profile create(Profile newProfile) {
        return profileRepository.save(newProfile);
    }

    @Override
    @Transactional
    public Profile update(Profile profile, Long id) {
        return profileRepository.save(profile);
    }

    @Override
    @ValidateWith(ProfileDeleteRequestValidator.class)
    @Transactional
    public void delete(Long id) {
        profileRepository.delete(id);
    }

    @Override
    @Transactional
    public Profile create() {
        Profile profile = new Profile();
        return profileRepository.save(profile);
    }
}
