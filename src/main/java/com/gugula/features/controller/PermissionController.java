package com.gugula.features.controller;

import com.gugula.features.controller.security.AuthorizeAdmin;
import com.gugula.features.entity.Permission;
import com.gugula.features.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gugula.features.controller.ApiVersion.CURRENT_API_VERSION;

@RestController
@RequestMapping(CURRENT_API_VERSION + "/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @AuthorizeAdmin
    @GetMapping
    List<Permission> getAllPermissions() {
        return permissionService.findAll();
    }

    @AuthorizeAdmin
    @PostMapping
    Permission createPermission(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    @AuthorizeAdmin
    @GetMapping(path = "/{id}")
    Permission getUser(@PathVariable Long id) {
        return permissionService.getPermission(id);
    }

    @AuthorizeAdmin
    @PutMapping(path = "/{id}")
    Permission getUserPermissions(@PathVariable Long id,
                                  @RequestBody Permission permission) {
        return permissionService.createOrReplace(id, permission);
    }
}
