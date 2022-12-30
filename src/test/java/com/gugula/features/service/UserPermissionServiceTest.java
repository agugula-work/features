package com.gugula.features.service;

import com.gugula.features.entity.Permission;
import com.gugula.features.entity.User;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPermissionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PermissionService permissionService;

    private UserPermissionService userPermissionService;

    private final static Permission PERMISSION_1 = Permission.builder().name("permission1").build();
    private final static Permission PERMISSION_2 = Permission.builder().name("permission2").build();
    private final static User USER_1 = User.builder().id(1L).name("username1").permissions(Sets.set(PERMISSION_1)).build();
    private final static User USER_2 = User.builder().id(2L).name("username2").permissions(Sets.set(PERMISSION_1, PERMISSION_2)).build();

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        userPermissionService = new UserPermissionService(userService, permissionService);
    }

    private static Stream<Arguments> userAndPermissionData() {

        return Stream.of(
                Arguments.of(Set.of(), Set.of(), Set.of()),
                Arguments.of(Set.of(PERMISSION_1), Set.of(PERMISSION_1), Set.of(PERMISSION_1)),
                Arguments.of(Set.of(PERMISSION_1, PERMISSION_2), Set.of(), Set.of(PERMISSION_1, PERMISSION_2)),
                Arguments.of(Set.of(), Set.of(PERMISSION_1, PERMISSION_2), Set.of(PERMISSION_1, PERMISSION_2)),
                Arguments.of(Set.of(PERMISSION_1), Set.of(PERMISSION_2), Set.of(PERMISSION_1, PERMISSION_2)),
                Arguments.of(Set.of(PERMISSION_1, PERMISSION_2), Set.of(PERMISSION_2), Set.of(PERMISSION_1, PERMISSION_2))
        );
    }

    @ParameterizedTest
    @MethodSource("userAndPermissionData")
    void getAllEnabledPermissionsForUser_shouldReturnBothPersonalAndGlobalPermissions(Set<Permission> userPermissions,
                                                                                      Set<Permission> globalPermissions,
                                                                                      Set<Permission> expectedResult) {
        final Long id = 123L;
        final User user = User.builder()
                .id(id)
                .permissions(userPermissions)
                .build();
        when(userService.getUser(id))
                .thenReturn(user);
        when(permissionService.findByGlobal(true))
                .thenReturn(globalPermissions);

        Set<Permission> result = userPermissionService.getAllEnabledPermissionsForUser(id);

        assertTrue(result.size() == expectedResult.size() && result.containsAll(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("userAndPermissionData")
    void getAllEnabledPermissionsForUserName_shouldReturnBothPersonalAndGlobalPermissions(Set<Permission> userPermissions,
                                                                                          Set<Permission> globalPermissions,
                                                                                          Set<Permission> expectedResult) {
        final String name = "MichaelScott";
        final User user = User.builder()
                .name(name)
                .permissions(userPermissions)
                .build();
        when(userService.getUserByName(name))
                .thenReturn(user);
        when(permissionService.findByGlobal(true))
                .thenReturn(globalPermissions);

        Set<Permission> result = userPermissionService.getAllEnabledPermissionsForUserName(name);

        assertTrue(result.size() == expectedResult.size() && result.containsAll(expectedResult));
    }

    private static Stream<Arguments> addPermissionForUserDataSource() {
        return Stream.of(
                Arguments.of(USER_1, PERMISSION_1, USER_1),
                Arguments.of(USER_1, PERMISSION_2, USER_1.toBuilder().permissions(Set.of(PERMISSION_1, PERMISSION_2)).build())
        );
    }

    @ParameterizedTest
    @MethodSource("addPermissionForUserDataSource")
    void addPermissionForUser(User user, Permission permission, User expectedSavedUser) {
        when(userService.getUser(user.getId()))
                .thenReturn(user);

        when(permissionService.getPermissionByName(permission.getName()))
                .thenReturn(permission);

        userPermissionService.addPermissionForUser(user.getId(), permission.getName());

        Mockito.verify(userService).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(expectedSavedUser, savedUser);
    }
}