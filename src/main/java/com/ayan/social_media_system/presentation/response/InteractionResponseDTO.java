package com.ayan.social_media_system.presentation.response;

import com.ayan.social_media_system.datalayer.InteractionType;

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
public class InteractionResponseDTO {

    private Long id;

    private Long userId;

    private String username;

    private Long postId;

    private InteractionType type;

    private String commentText;

    private LocalDateTime createdAt;
}