package com.opencbs.genesis.helpers;

import com.opencbs.genesis.domain.BaseEntity;

/**
 * Created by Makhsut Islamov on 25.10.2016.
 */
public class EntityHelper {

    public static <T extends BaseEntity>boolean isNew( T entity){
        return entity.getId() == null || entity.getId() == 0;
    }

    public static <T extends BaseEntity> boolean isPersist(T entity){
        return !isNew(entity);
    }
}