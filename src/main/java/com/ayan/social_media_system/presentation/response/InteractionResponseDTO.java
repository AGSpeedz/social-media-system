package com.ayan.social_media_system.presentation.response;

import com.ayan.social_media_system.domain.InteractionType;
import java.time.LocalDateTime;

public record InteractionResponseDTO(
        Long id,
        Long userId,
        String username,
        Long postId,
        InteractionType type,
        String commentText,
        LocalDateTime createdAt
) {
}
