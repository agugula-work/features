package com.gugula.features.service;

import com.gugula.features.entity.Permission;
import com.gugula.features.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserPermissionService {

    private final UserService userService;
    private final PermissionService permissionService;

    public Set<Permission> getAllEnabledPermissionsForUser(Long id) {
        return getAllEnabledPermissionsForUser(userService.getUser(id));
    }

    public Set<Permission> getAllEnabledPermissionsForUserName(String name) {
        return getAllEnabledPermissionsForUser(userService.getUserByName(name));
    }

    private Set<Permission> getAllEnabledPermissionsForUser(User user) {
        Set<Permission> assignedPermissions = user.getPermissions();
        Set<Permission> globalPermissions = permissionService.findByGlobal(true);

        return Stream.concat(assignedPermissions.stream(), globalPermissions.stream())
                .collect(Collectors.toSet());
    }

    @Transactional
    public User addPermissionForUser(Long userId, String permissionName) {
        User user = userService.getUser(userId);
        Permission permission = permissionService.getPermissionByName(permissionName);
        user.getPermissions().add(permission);
        return userService.save(user);
    }
}
