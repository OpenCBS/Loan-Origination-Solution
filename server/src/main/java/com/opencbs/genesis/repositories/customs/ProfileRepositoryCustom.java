package com.opencbs.genesis.repositories.customs;

import com.opencbs.genesis.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Makhsut Islamov on 14.03.2017.
 */
public interface ProfileRepositoryCustom {
    Page<Profile> findBy(String query, Pageable pageable);
}
