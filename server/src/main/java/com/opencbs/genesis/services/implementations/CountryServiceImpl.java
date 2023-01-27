package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Country;
import com.opencbs.genesis.repositories.CountryRepository;
import com.opencbs.genesis.services.CountryService;
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
public class CountryServiceImpl implements CountryService, LookupService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Country::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Country> findAll(Pageable pageable, String searchString) {
        if (!StringUtils.isEmpty(searchString)) {
            return this.countryRepository.findAllByNameContainsIgnoreCase(pageable, searchString);
        }
        return this.countryRepository.findAll(pageable);
    }

    public Optional<Country> findOne(long id) {
        return Optional.of(this.countryRepository.findOne(id));
    }

    @Override
    public String getName() {
        return "countries";
    }

    @Override
    public String findById(Long id) {
        return countryRepository.findOne(id).getName();
    }
}
