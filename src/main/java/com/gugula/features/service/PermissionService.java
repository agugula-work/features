package com.gugula.features.service;

import com.gugula.features.controller.exception.BadRequestException;
import com.gugula.features.controller.exception.NotFoundException;
import com.gugula.features.entity.Permission;
import com.gugula.features.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {

    @Delegate
    private final PermissionRepository permissionRepository;

    public Permission getPermission(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Permission getPermissionByName(String name) {
        return permissionRepository.findByName(name)
                .orElseThrow(NotFoundException::new);
    }

    public Permission createOrReplace(Long id, Permission permission) {
        if (permission.getId() != null && !permission.getId().equals(id)) {
            throw new BadRequestException();
        }

        return permissionRepository.save(permission);
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
}
