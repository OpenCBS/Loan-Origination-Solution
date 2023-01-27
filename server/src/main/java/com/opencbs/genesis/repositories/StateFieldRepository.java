package com.opencbs.genesis.repositories;

import com.opencbs.genesis.domain.StateField;

import java.util.List;

/**
 * Created by Makhsut Islamov on 05.12.2016.
 */
public interface StateFieldRepository extends Repository<StateField> {
    List<StateField> findByStateId(Long stateId);
}
