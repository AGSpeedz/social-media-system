package com.ayan.social_media_system.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(
        @NotBlank(message = "Post content is required")
        @Size(max = 2000)
        String content,

        String mediaUrl,

        @NotNull(message = "Author ID is required")
        @Positive(message = "Author ID must be positive")
        Long authorId
) {
}
