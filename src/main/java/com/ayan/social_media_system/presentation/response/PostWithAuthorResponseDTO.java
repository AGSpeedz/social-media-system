package com.ayan.social_media_system.presentation.response;

public record PostWithAuthorResponseDTO(
        PostResponseDTO post,
        UserResponseDTO author
) {
}
