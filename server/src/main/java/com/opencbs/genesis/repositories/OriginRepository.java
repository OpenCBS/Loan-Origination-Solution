package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Origin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OriginRepository extends Repository<Origin>{
    Page<Origin> findAllByNameContainsIgnoreCase(Pageable pageable, String searchString);
}
