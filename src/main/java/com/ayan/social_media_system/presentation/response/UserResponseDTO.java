package com.ayan.social_media_system.presentation.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserResponseDTO {

    private Long id;

    private String username;

    private String email;

    private String displayName;
}
