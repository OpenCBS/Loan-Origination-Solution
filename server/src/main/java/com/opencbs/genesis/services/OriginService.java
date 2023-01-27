package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Origin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OriginService {
    List<Origin> findAll();

    Page<Origin> findAll(Pageable pageable, String searchString);

    Optional<Origin> findOne(long id);
}
