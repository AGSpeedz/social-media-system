package com.ayan.social_media_system.presentation.response;

import java.util.List;

public record UserWithPostsResponseDTO(
        UserResponseDTO user,
        List<PostResponseDTO> posts
) {
}
