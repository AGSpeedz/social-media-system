package com.ayan.social_media_system.service;

import com.ayan.social_media_system.datalayer.Post;
import com.ayan.social_media_system.datalayer.User;
import com.ayan.social_media_system.datalayer.PostRepository;
import com.ayan.social_media_system.datalayer.UserRepository;
import com.ayan.social_media_system.datalayer.InteractionRepository;
import com.ayan.social_media_system.exception.NotFoundException;
import com.ayan.social_media_system.mapper.SocialMediaMapper;
import com.ayan.social_media_system.presentation.response.PostResponseDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private SocialMediaMapper mapper;

    @InjectMocks
    private PostService postService;

    @Test
    void getPostsByAuthorReturnsCorrectPosts() {

        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(5L);
        post.setAuthor(user);
        post.setContent("Test post");

        PostResponseDTO expected = new PostResponseDTO(
                5L, 1L, "testuser",
                "Test post", null,
                LocalDateTime.now()
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(postRepository.findByAuthorId(1L))
                .thenReturn(List.of(post));

        when(mapper.toPostResponseDTO(post))
                .thenReturn(expected);

        List<PostResponseDTO> result =
                postService.getPostsByAuthor(1L);

        assertEquals(1, result.size());
        assertEquals(expected, result.get(0));
    }

    @Test
    void missingPostThrowsNotFoundException() {

        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> postService.getPostById(999L)
        );
    }

    @Test
    void missingAuthorThrowsNotFoundException() {

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> postService.getPostsByAuthor(999L)
        );
    }
}