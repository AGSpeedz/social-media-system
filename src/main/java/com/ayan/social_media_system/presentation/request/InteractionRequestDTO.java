package com.ayan.social_media_system.presentation.request;

import com.ayan.social_media_system.domain.InteractionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record InteractionRequestDTO(
        @NotNull(message = "Interaction type is required")
        InteractionType type,

        @Size(max = 1000)
        String commentText,

        @NotNull(message = "User ID is required")
        @Positive
        Long userId,

        @NotNull(message = "Post ID is required")
        @Positive
        Long postId
) {
}
