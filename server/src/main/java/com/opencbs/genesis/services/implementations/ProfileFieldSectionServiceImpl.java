package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.ProfileFieldSection;
import com.opencbs.genesis.repositories.ProfileFieldSectionRepository;
import com.opencbs.genesis.services.ProfileFieldSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileFieldSectionServiceImpl implements ProfileFieldSectionService {

    private final ProfileFieldSectionRepository repository;

    @Override
    public List<ProfileFieldSection> findAll() {
        return this.repository.findAll(new Sort("order"));
    }
}
