package com.gugula.features.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gugula.features.entity.User;
import com.gugula.features.service.UserPermissionService;
import com.gugula.features.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Mock
    private UserPermissionService userPermissionService;
    @Mock
    private UserService userService;

    private final static User USER_1 = User.builder().name("username1").build();
    private final static User USER_2 = User.builder().name("username2").build();
    private final static User USER_3 = User.builder().name("username3").build();

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        UserController userController = new UserController(userPermissionService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers_returnsAllUsers() throws Exception {
        List<User> allUsers = List.of(USER_1, USER_2, USER_3);

        when(userService.findAll())
                .thenReturn(allUsers);

        mockMvc.perform(get(ApiVersion.CURRENT_API_PREFIX + "/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(allUsers)));
    }
}