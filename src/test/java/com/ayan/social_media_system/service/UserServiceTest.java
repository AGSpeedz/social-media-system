package com.ayan.social_media_system.service;

import com.ayan.social_media_system.datalayer.InteractionRepository;
import com.ayan.social_media_system.datalayer.PostRepository;
import com.ayan.social_media_system.datalayer.User;
import com.ayan.social_media_system.datalayer.UserRepository;
import com.ayan.social_media_system.exception.NotFoundException;
import com.ayan.social_media_system.mapper.SocialMediaMapper;
import com.ayan.social_media_system.presentation.request.UserRequestDTO;
import com.ayan.social_media_system.presentation.response.UserResponseDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private SocialMediaMapper mapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserByIdReturnsUser() {

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        UserResponseDTO expected = new UserResponseDTO(
                1L,
                "testuser",
                "test@example.com",
                "Test User"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(mapper.toUserResponseDTO(user))
                .thenReturn(expected);

        UserResponseDTO result =
                userService.getUserById(1L);

        assertEquals(expected, result);
    }

    @Test
    void missingUserThrowsNotFoundException() {

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> userService.getUserById(999L)
        );
    }

    @Test
    void duplicateUsernameIsRejected() {

        User existing = new User();
        existing.setId(1L);
        existing.setUsername("takenuser");

        UserRequestDTO request = new UserRequestDTO(
                "takenuser",
                "another@example.com",
                "Another User"
        );

        when(userRepository.findByUsername("takenuser"))
                .thenReturn(Optional.of(existing));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(request)
        );

        assertEquals(
                "Username already exists",
                exception.getMessage()
        );

        verify(userRepository, never()).save(any(User.class));
    }
}
