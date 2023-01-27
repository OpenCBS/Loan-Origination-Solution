package com.opencbs.genesis.services.implementations;

import com.opencbs.genesis.domain.enums.SystemPermissions;
import com.opencbs.genesis.dto.CodeNameDto;
import com.opencbs.genesis.services.SystemPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makhsut Islamov on 16.12.2016.
 */
@Service
public class SystemPermissionServiceImpl extends BaseService implements SystemPermissionService {
    @Override
    public List<CodeNameDto> getAll() {
        List<CodeNameDto> result = new ArrayList<>();
        for (SystemPermissions systemPermission : SystemPermissions.values()) {
            result.add(new CodeNameDto(systemPermission.name(), systemPermission.getDescription()));
        }

        return result;
    }
}
