package com.ayan.social_media_system.service;

import com.ayan.social_media_system.domain.Post;
import com.ayan.social_media_system.domain.User;
import com.ayan.social_media_system.exception.NotFoundException;
import com.ayan.social_media_system.mapper.SocialMediaMapper;
import com.ayan.social_media_system.presentation.request.UserRequestDTO;
import com.ayan.social_media_system.presentation.response.UserResponseDTO;
import com.ayan.social_media_system.presentation.response.UserWithPostsResponseDTO;
import com.ayan.social_media_system.repository.InteractionRepository;
import com.ayan.social_media_system.repository.PostRepository;
import com.ayan.social_media_system.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final InteractionRepository interactionRepository;
    private final SocialMediaMapper mapper;

    public UserService(
            UserRepository userRepository,
            PostRepository postRepository,
            InteractionRepository interactionRepository,
            SocialMediaMapper mapper
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.interactionRepository = interactionRepository;
        this.mapper = mapper;
    }

    // GET ALL USERS
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toUserResponseDTO)
                .toList();
    }

    // GET USER BY ID
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = findUser(id);
        return mapper.toUserResponseDTO(user);
    }

    // CREATE USER
    public UserResponseDTO createUser(UserRequestDTO request) {
        validateUniqueUser(request, null);

        User user = mapper.toUserEntity(request);
        User savedUser = userRepository.save(user);

        return mapper.toUserResponseDTO(savedUser);
    }

    // UPDATE USER
    public UserResponseDTO updateUser(
            Long id,
            UserRequestDTO request
    ) {
        User user = findUser(id);

        validateUniqueUser(request, id);
        mapper.updateUserEntity(user, request);

        User savedUser = userRepository.save(user);
        return mapper.toUserResponseDTO(savedUser);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        User user = findUser(id);

        // Remove interactions performed by this user.
        interactionRepository.deleteAll(
                interactionRepository.findByUserId(id)
        );
        interactionRepository.flush();

        // Find posts created by this user.
        List<Post> posts = postRepository.findByAuthorId(id);

        // Remove interactions on those posts.
        for (Post post : posts) {
            interactionRepository.deleteAll(
                    interactionRepository.findByPostId(post.getId())
            );
        }
        interactionRepository.flush();

        // Remove the user's posts.
        postRepository.deleteAll(posts);
        postRepository.flush();

        // Finally remove the user.
        userRepository.delete(user);
    }

    // GET USER WITH POSTS
    @Transactional(readOnly = true)
    public UserWithPostsResponseDTO getUserWithPosts(Long id) {
        User user = findUser(id);
        List<Post> posts = postRepository.findByAuthorId(id);

        return mapper.toUserWithPostsResponseDTO(user, posts);
    }

    // FIND USER OR THROW EXCEPTION
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "User " + id + " not found"
                        )
                );
    }

    // CHECK UNIQUE USERNAME AND EMAIL
    private void validateUniqueUser(
            UserRequestDTO request,
            Long currentId
    ) {
        userRepository.findByUsername(request.username())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(currentId)) {
                        throw new IllegalArgumentException(
                                "Username already exists"
                        );
                    }
                });

        userRepository.findByEmail(request.email())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(currentId)) {
                        throw new IllegalArgumentException(
                                "Email already exists"
                        );
                    }
                });
    }
}
