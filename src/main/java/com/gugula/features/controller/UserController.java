package com.gugula.features.controller;

import com.gugula.features.controller.security.AuthorizeAdmin;
import com.gugula.features.entity.Permission;
import com.gugula.features.entity.User;
import com.gugula.features.service.UserPermissionService;
import com.gugula.features.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.gugula.features.controller.ApiVersion.CURRENT_API_PREFIX;

@RestController
@RequestMapping(CURRENT_API_PREFIX + "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserPermissionService userPermissionService;
    private final UserService userService;

    @AuthorizeAdmin
    @GetMapping
    List<User> getAllUsers() {
        return userService.findAll();
    }

    @AuthorizeAdmin
    @GetMapping(path = "/{id}")
    User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @AuthorizeAdmin
    @GetMapping(path = "/{id}/permissions")
    Set<Permission> getUserPermissions(@PathVariable Long id) {
        return userService.getUser(id).getPermissions();
    }

    @AuthorizeAdmin
    @GetMapping(path = "/{id}/all-enabled-permissions")
    Set<Permission> getAllPermissionsEnabledForUser(@PathVariable Long id) {
        return userPermissionService.getAllEnabledPermissionsForUser(id);
    }

    @AuthorizeAdmin
    @PostMapping(path = "/{id}/permissions")
    User addUserPermissions(@PathVariable Long id, @RequestBody String permissionName) {
        return userPermissionService.addPermissionForUser(id, permissionName);
    }

    @GetMapping(path = "/current/permissions")
    Set<Permission> getCurrentUserPermissions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return userPermissionService.getAllEnabledPermissionsForUserName(user.getUsername());
    }
}
