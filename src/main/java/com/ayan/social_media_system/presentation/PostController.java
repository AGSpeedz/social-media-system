package com.ayan.social_media_system.presentation;

import com.ayan.social_media_system.presentation.request.PostRequestDTO;
import com.ayan.social_media_system.presentation.response.PostResponseDTO;
import com.ayan.social_media_system.presentation.response.PostWithAuthorResponseDTO;
import com.ayan.social_media_system.presentation.response.InteractionResponseDTO;
import com.ayan.social_media_system.service.PostService;
import com.ayan.social_media_system.service.InteractionService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final InteractionService interactionService;

    public PostController(
            PostService postService,
            InteractionService interactionService
    ) {
        this.postService = postService;
        this.interactionService = interactionService;
    }

    // GET ALL POSTS
    @GetMapping(params = "!authorId")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // GET POSTS FILTERED BY AUTHOR ID
    @GetMapping(params = "authorId")
    public ResponseEntity<List<PostResponseDTO>> getPostsByAuthor(
            @RequestParam Long authorId
    ) {
        return ResponseEntity.ok(
                postService.getPostsByAuthor(authorId)
        );
    }

    // GET POST BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // CREATE POST
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(
            @Valid @RequestBody PostRequestDTO request
    ) {
        PostResponseDTO created = postService.createPost(request);

        URI location = URI.create("/api/posts/" + created.getId());

        return ResponseEntity.created(location).body(created);
    }

    // UPDATE POST
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDTO request
    ) {
        return ResponseEntity.ok(
                postService.updatePost(id, request)
        );
    }

    // DELETE POST
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // GET POST WITH AUTHOR
    @GetMapping("/{id}/author")
    public ResponseEntity<PostWithAuthorResponseDTO> getPostWithAuthor(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                postService.getPostWithAuthor(id)
        );
    }

    // GET ALL INTERACTIONS ON A POST
    @GetMapping("/{id}/interactions")
    public ResponseEntity<List<InteractionResponseDTO>> getPostInteractions(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                interactionService.getInteractionsByPostId(id)
        );
    }
}
