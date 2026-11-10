package com.ayan.social_media_system.presentation.request;

import jakarta.validation.constraints.NotBlank;
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
public class PostRequestDTO {

        @NotBlank(message = "Post content is required")
        @Size(max = 2000)
        private String content;

        private String mediaUrl;

        @NotNull(message = "Author ID is required")
        @Positive(message = "Author ID must be positive")
        private Long authorId;
}
