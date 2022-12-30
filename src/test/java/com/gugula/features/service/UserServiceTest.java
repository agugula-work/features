package com.gugula.features.service;

import com.gugula.features.controller.exception.NotFoundException;
import com.gugula.features.entity.User;
import com.gugula.features.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private final static User USER = User.builder()
            .id(123L)
            .name("username")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void save_shouldUseRepository() {
        userService.save(USER);

        verify(userRepository).save(USER);
    }

    @Test
    void getUser_shouldThrowWhenEntityNotFound() {
        assertThrows(NotFoundException.class, () -> userService.getUser(123L));
    }

    @Test
    void getUser_shouldReturnExistingEntity() {
        when(userRepository.findById(123L))
                .thenReturn(Optional.of(USER));

        User result = userService.getUser(123L);

        assertEquals(USER, result);
    }

    @Test
    void getUserByName_shouldThrowWhenEntityNotFound() {
        assertThrows(NotFoundException.class, () -> userService.getUserByName("testtest"));
    }

    @Test
    void getUserByName_shouldReturnExistingEntity() {
        when(userRepository.findByName("username"))
                .thenReturn(Optional.of(USER));

        User result = userService.getUserByName("username");

        assertEquals(USER, result);
    }

}