package com.gugula.features.config.security;

import com.gugula.features.entity.User;
import com.gugula.features.repository.UserRepository;
import com.gugula.features.service.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPermissionService userPermissionService;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return userRepository.findByName(username)
                .map(account -> {
                    Set<GrantedAuthority> authoritiesEnabledForAccount = getAuthoritiesEnabledForAccount(account);
                    return new CustomUserDetails(account, authoritiesEnabledForAccount);
                })
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private Set<GrantedAuthority> getAuthoritiesEnabledForAccount(User user) {
        return userPermissionService.getAllEnabledPermissionsForUser(user.getId()).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());
    }
}
