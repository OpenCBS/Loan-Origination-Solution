package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.exceptions.LookupNotImplementedException;
import com.opencbs.genesis.services.LookupService;
import com.opencbs.genesis.services.LookupValueExtractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupValueExtractServiceImpl implements LookupValueExtractService {

    private final List<LookupService> availableLookups;

    @Override
    public String getValue(String className, String value) {
        LookupService lookupService = availableLookups.stream().filter(it -> it.getName().equals(className))
                .findFirst().orElseThrow(() -> new LookupNotImplementedException(className));
        return lookupService.findById(Long.valueOf(value));
    }
}
