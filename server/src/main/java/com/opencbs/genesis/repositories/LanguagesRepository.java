package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LanguagesRepository extends Repository<Language>{
    Page<Language> findAllByNameContainsIgnoreCase(Pageable pageable, String searchString);
}
