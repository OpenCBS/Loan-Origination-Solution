package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CountryService  {
    List<Country> findAll();

    Page<Country> findAll(Pageable pageable, String searchString);

    Optional<Country> findOne(long id);
}
