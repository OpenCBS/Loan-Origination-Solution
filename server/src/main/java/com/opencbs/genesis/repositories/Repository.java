package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Makhsut Islamov on 03.11.2016.
 */
@NoRepositoryBean
public interface Repository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
