package com.ayan.social_media_system.controller;

import com.ayan.social_media_system.presentation.PostController;
import com.ayan.social_media_system.presentation.request.PostRequestDTO;
import com.ayan.social_media_system.presentation.response.PostResponseDTO;
import com.ayan.social_media_system.service.PostService;
import com.ayan.social_media_system.service.InteractionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private InteractionService interactionService;

    @InjectMocks
    private PostController postController;

    @Test
    void getAllPostsReturns200() {

        PostResponseDTO post = new PostResponseDTO(
                1L, 1L, "testuser",
                "Hello World", null,
                LocalDateTime.now()
        );

        when(postService.getAllPosts())
                .thenReturn(List.of(post));

        ResponseEntity<List<PostResponseDTO>> response =
                postController.getAllPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createPostReturns201() {

        PostRequestDTO request =
                new PostRequestDTO("New Post", null, 1L);

        PostResponseDTO created = new PostResponseDTO(
                11L, 1L, "testuser",
                "New Post", null,
                LocalDateTime.now()
        );

        when(postService.createPost(request))
                .thenReturn(created);

        ResponseEntity<PostResponseDTO> response =
                postController.createPost(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        assertEquals(
                "/api/posts/11",
                response.getHeaders().getLocation().toString()
        );
    }

    @Test
    void getPostsByAuthorReturns200() {

        when(postService.getPostsByAuthor(1L))
                .thenReturn(List.of());

        ResponseEntity<List<PostResponseDTO>> response =
                postController.getPostsByAuthor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(postService).getPostsByAuthor(1L);
    }

    @Test
    void deletePostReturns204() {

        ResponseEntity<Void> response =
                postController.deletePost(1L);

        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(postService).deletePost(1L);
    }
}
