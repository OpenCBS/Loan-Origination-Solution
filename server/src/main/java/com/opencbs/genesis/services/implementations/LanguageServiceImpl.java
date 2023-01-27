package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Language;
import com.opencbs.genesis.repositories.LanguagesRepository;
import com.opencbs.genesis.services.LanguageService;
import com.opencbs.genesis.services.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LanguageServiceImpl implements LanguageService, LookupService {

    private final LanguagesRepository languagesRepository;

    @Override
    public List<Language> findAll() {
        return this.languagesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Language::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Language> findAll(Pageable pageable, String searchString) {
        if (!StringUtils.isEmpty(searchString)) {
            return this.languagesRepository.findAllByNameContainsIgnoreCase(pageable, searchString);
        }
        return this.languagesRepository.findAll(pageable);
    }

    @Override
    public Optional<Language> findOne(long id) {
        return Optional.of(this.languagesRepository.findOne(id));
    }

    @Override
    public String getName() {
        return "languages";
    }

    @Override
    public String findById(Long id) {
        return languagesRepository.findOne(id).getName();
    }
}
