package com.ayan.social_media_system.presentation.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostResponseDTO {

    private Long id;

    private Long authorId;

    private String authorUsername;

    private String content;

    private String mediaUrl;

    private LocalDateTime createdAt;
}
