package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.repositories.customs.ProfileRepositoryCustom;

import java.util.List;

/**
 * Created by Makhsut Islamov on 20.10.2016.
 */
public interface ProfileRepository extends Repository<Profile>, ProfileRepositoryCustom  {
    List<Profile> findAllByIdIn(List<Long> participantsAsProfile);
}
