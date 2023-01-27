package com.opencbs.genesis.services;

import com.opencbs.genesis.domain.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    List<Language> findAll();

    Page<Language> findAll(Pageable pageable, String searchString);

    Optional<Language> findOne(long id);
}
