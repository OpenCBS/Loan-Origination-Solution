package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CountryRepository extends Repository<Country>{

    Page<Country> findAllByNameContainsIgnoreCase(Pageable pageable, String searchString);
}
