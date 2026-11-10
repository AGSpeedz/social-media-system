package com.ayan.social_media_system.presentation;

import com.ayan.social_media_system.presentation.request.InteractionRequestDTO;
import com.ayan.social_media_system.presentation.response.InteractionResponseDTO;
import com.ayan.social_media_system.service.InteractionService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    // GET ALL INTERACTIONS
    @GetMapping
    public ResponseEntity<List<InteractionResponseDTO>> getAllInteractions() {
        return ResponseEntity.ok(
                interactionService.getAllInteractions()
        );
    }

    // GET INTERACTION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<InteractionResponseDTO> getInteractionById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                interactionService.getInteractionById(id)
        );
    }

    // CREATE INTERACTION
    @PostMapping
    public ResponseEntity<InteractionResponseDTO> createInteraction(
            @Valid @RequestBody InteractionRequestDTO request
    ) {
        InteractionResponseDTO created =
                interactionService.createInteraction(request);

        URI location = URI.create(
                "/api/interactions/" + created.id()
        );

        return ResponseEntity.created(location).body(created);
    }

    // UPDATE INTERACTION
    @PutMapping("/{id}")
    public ResponseEntity<InteractionResponseDTO> updateInteraction(
            @PathVariable Long id,
            @Valid @RequestBody InteractionRequestDTO request
    ) {
        return ResponseEntity.ok(
                interactionService.updateInteraction(id, request)
        );
    }

    // DELETE INTERACTION
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteraction(
            @PathVariable Long id
    ) {
        interactionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }
}
