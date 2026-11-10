package com.ayan.social_media_system.presentation;

import com.ayan.social_media_system.presentation.request.UserRequestDTO;
import com.ayan.social_media_system.presentation.response.UserResponseDTO;
import com.ayan.social_media_system.presentation.response.UserWithPostsResponseDTO;
import com.ayan.social_media_system.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // CREATE USER
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request
    ) {
        UserResponseDTO created = userService.createUser(request);

        URI location = URI.create("/api/users/" + created.getId());

        return ResponseEntity.created(location).body(created);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request
    ) {
        return ResponseEntity.ok(
                userService.updateUser(id, request)
        );
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // GET USER WITH POSTS
    @GetMapping("/{id}/posts")
    public ResponseEntity<UserWithPostsResponseDTO> getUserPosts(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userService.getUserWithPosts(id)
        );
    }
}
