package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.Origin;
import com.opencbs.genesis.repositories.OriginRepository;
import com.opencbs.genesis.services.LookupService;
import com.opencbs.genesis.services.OriginService;
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
public class OriginServiceImpl implements OriginService, LookupService {

    private final OriginRepository originRepository;

    @Override
    public List<Origin> findAll() {
        return this.originRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Origin::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Origin> findAll(Pageable pageable, String searchString) {
        if(!StringUtils.isEmpty(searchString)){
            return this.originRepository.findAllByNameContainsIgnoreCase(pageable, searchString);
        }
        return this.originRepository.findAll(pageable);
    }

    @Override
    public Optional<Origin> findOne(long id) {
        return Optional.of(this.originRepository.findOne(id));
    }

    @Override
    public String getName() {
        return "origins";
    }

    @Override
    public String findById(Long id) {
        return originRepository.getOne(id).getName();
    }
}
