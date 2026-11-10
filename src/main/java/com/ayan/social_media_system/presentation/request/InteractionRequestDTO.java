package com.ayan.social_media_system.presentation.request;

import com.ayan.social_media_system.datalayer.InteractionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InteractionRequestDTO {

        @NotNull(message = "Interaction type is required")
        private InteractionType type;

        @Size(max = 1000)
        private String commentText;

        @NotNull(message = "User ID is required")
        @Positive(message = "User ID must be positive")
        private Long userId;

        @NotNull(message = "Post ID is required")
        @Positive(message = "Post ID must be positive")
        private Long postId;
}
