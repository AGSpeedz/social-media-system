package com.ayan.social_media_system.controller;

import com.ayan.social_media_system.presentation.UserController;
import com.ayan.social_media_system.presentation.request.UserRequestDTO;
import com.ayan.social_media_system.presentation.response.UserResponseDTO;
import com.ayan.social_media_system.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getAllUsersReturns200() {

        UserResponseDTO user = new UserResponseDTO(
                1L, "testuser", "test@example.com", "Test User"
        );

        when(userService.getAllUsers())
                .thenReturn(List.of(user));

        ResponseEntity<List<UserResponseDTO>> response =
                userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createUserReturns201() {

        UserRequestDTO request = new UserRequestDTO(
                "newuser", "new@example.com", "New User"
        );

        UserResponseDTO created = new UserResponseDTO(
                11L, "newuser", "new@example.com", "New User"
        );

        when(userService.createUser(request))
                .thenReturn(created);

        ResponseEntity<UserResponseDTO> response =
                userController.createUser(request);

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertEquals(created, response.getBody());
        assertEquals(
                "/api/users/11",
                response.getHeaders().getLocation().toString()
        );
    }

    @Test
    void deleteUserReturns204() {

        ResponseEntity<Void> response =
                userController.deleteUser(1L);

        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(userService).deleteUser(1L);
    }
}