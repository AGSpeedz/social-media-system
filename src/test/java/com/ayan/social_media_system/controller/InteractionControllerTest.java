package com.ayan.social_media_system.controller;

import com.ayan.social_media_system.datalayer.InteractionType;
import com.ayan.social_media_system.presentation.InteractionController;
import com.ayan.social_media_system.presentation.request.InteractionRequestDTO;
import com.ayan.social_media_system.presentation.response.InteractionResponseDTO;
import com.ayan.social_media_system.service.InteractionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteractionControllerTest {

    @Mock
    private InteractionService interactionService;

    @InjectMocks
    private InteractionController interactionController;

    @Test
    void getAllInteractionsReturns200() {

        InteractionResponseDTO interaction =
                new InteractionResponseDTO(
                        1L, 2L, "testuser", 1L,
                        InteractionType.LIKE, null,
                        LocalDateTime.now()
                );

        when(interactionService.getAllInteractions())
                .thenReturn(List.of(interaction));

        ResponseEntity<List<InteractionResponseDTO>> response =
                interactionController.getAllInteractions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createInteractionReturns201() {

        InteractionRequestDTO request =
                new InteractionRequestDTO(
                        InteractionType.LIKE,
                        null,
                        2L,
                        1L
                );

        InteractionResponseDTO created =
                new InteractionResponseDTO(
                        12L, 2L, "testuser", 1L,
                        InteractionType.LIKE, null,
                        LocalDateTime.now()
                );

        when(interactionService.createInteraction(request))
                .thenReturn(created);

        ResponseEntity<InteractionResponseDTO> response =
                interactionController.createInteraction(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        assertEquals(
                "/api/interactions/12",
                response.getHeaders().getLocation().toString()
        );
    }

    @Test
    void deleteInteractionReturns204() {

        ResponseEntity<Void> response =
                interactionController.deleteInteraction(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(interactionService).deleteInteraction(1L);
    }
}
