package com.ayan.social_media_system.presentation.response;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String displayName
) {
}
