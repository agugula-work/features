package com.gugula.features.controller;

import com.gugula.features.entity.Permission;
import com.gugula.features.entity.User;
import com.gugula.features.repository.PermissionRepository;
import com.gugula.features.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSecurityTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        Permission permission = Permission.builder()
                .name("rank-bears")
                .build();
        User user = User.builder()
                .name("DwightSchmidt")
                .permissions(Set.of(permission))
                .build();
        userRepository.save(user);
    }

    @Test
    void getAllUsers_shouldDenyAccessToAnonymousUser() throws Exception {
        mockMvc.perform(get(ApiVersion.CURRENT_API_PREFIX + "/users"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "DwightSchmidt")
    void getAllUsers_shouldDenyAccessToUnauthorizedUser() throws Exception {
        mockMvc.perform(get(ApiVersion.CURRENT_API_PREFIX + "/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "admin")
    void getAllUsers_shouldAllowAccessToAdmin() throws Exception {
        mockMvc.perform(get(ApiVersion.CURRENT_API_PREFIX + "/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getCurrentUserPermissions_shouldDenyAccessToAnonymousUser() throws Exception {
        mockMvc.perform(get(ApiVersion.CURRENT_API_PREFIX + "/users/current/permissions"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "DwightSchmidt", authorities = {"rank-bears"})
    void getCurrentUserPermissions_shouldAllowAccessToUnauthorizedUser() throws Exception {
        mockMvc.perform(get(ApiVersion.CURRENT_API_PREFIX + "/users/current/permissions"))
                .andExpect(status().isOk());
    }
}