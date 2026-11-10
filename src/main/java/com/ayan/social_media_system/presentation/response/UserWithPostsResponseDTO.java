package com.ayan.social_media_system.presentation.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserWithPostsResponseDTO {

    private UserResponseDTO user;

    private List<PostResponseDTO> posts;
}