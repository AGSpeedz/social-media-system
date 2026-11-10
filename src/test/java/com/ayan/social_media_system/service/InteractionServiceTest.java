package com.ayan.social_media_system.service;

import com.ayan.social_media_system.datalayer.*;
import com.ayan.social_media_system.exception.NotFoundException;
import com.ayan.social_media_system.mapper.SocialMediaMapper;
import com.ayan.social_media_system.presentation.request.InteractionRequestDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteractionServiceTest {

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private SocialMediaMapper mapper;

    @InjectMocks
    private InteractionService interactionService;

    @Test
    void commentRequiresText() {

        InteractionRequestDTO request =
                new InteractionRequestDTO(
                        InteractionType.COMMENT,
                        "",
                        1L,
                        2L
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> interactionService.createInteraction(request)
        );

        verifyNoInteractions(interactionRepository);
    }

    @Test
    void commentRejectsNullText() {

        InteractionRequestDTO request =
                new InteractionRequestDTO(
                        InteractionType.COMMENT,
                        null,
                        1L,
                        2L
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> interactionService.createInteraction(request)
        );
    }

    @Test
    void missingUserThrowsNotFoundException() {

        InteractionRequestDTO request =
                new InteractionRequestDTO(
                        InteractionType.LIKE,
                        null,
                        999L,
                        1L
                );

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> interactionService.createInteraction(request)
        );
    }

    @Test
    void missingPostThrowsNotFoundException() {

        User user = new User();
        user.setId(1L);

        InteractionRequestDTO request =
                new InteractionRequestDTO(
                        InteractionType.LIKE,
                        null,
                        1L,
                        999L
                );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> interactionService.createInteraction(request)
        );
    }
}
