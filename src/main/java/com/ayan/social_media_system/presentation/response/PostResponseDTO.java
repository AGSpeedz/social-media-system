package com.ayan.social_media_system.presentation.response;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        Long authorId,
        String authorUsername,
        String content,
        String mediaUrl,
        LocalDateTime createdAt
) {
}
