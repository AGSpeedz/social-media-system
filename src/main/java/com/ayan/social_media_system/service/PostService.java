package com.ayan.social_media_system.service;

import com.ayan.social_media_system.domain.Post;
import com.ayan.social_media_system.domain.User;
import com.ayan.social_media_system.exception.NotFoundException;
import com.ayan.social_media_system.mapper.SocialMediaMapper;
import com.ayan.social_media_system.presentation.request.PostRequestDTO;
import com.ayan.social_media_system.presentation.response.PostResponseDTO;
import com.ayan.social_media_system.presentation.response.PostWithAuthorResponseDTO;
import com.ayan.social_media_system.repository.InteractionRepository;
import com.ayan.social_media_system.repository.PostRepository;
import com.ayan.social_media_system.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final InteractionRepository interactionRepository;
    private final SocialMediaMapper mapper;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            InteractionRepository interactionRepository,
            SocialMediaMapper mapper
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.interactionRepository = interactionRepository;
        this.mapper = mapper;
    }

    // GET ALL POSTS
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(mapper::toPostResponseDTO)
                .toList();
    }

    // GET POST BY ID
    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long id) {
        Post post = findPost(id);
        return mapper.toPostResponseDTO(post);
    }

    // CREATE POST
    public PostResponseDTO createPost(PostRequestDTO request) {
        User author = findUser(request.authorId());

        Post post = mapper.toPostEntity(request, author);
        Post savedPost = postRepository.save(post);

        return mapper.toPostResponseDTO(savedPost);
    }

    // UPDATE POST
    public PostResponseDTO updatePost(
            Long id,
            PostRequestDTO request
    ) {
        Post post = findPost(id);
        User author = findUser(request.authorId());

        mapper.updatePostEntity(post, request, author);

        Post savedPost = postRepository.save(post);
        return mapper.toPostResponseDTO(savedPost);
    }

    // DELETE POST
    public void deletePost(Long id) {
        Post post = findPost(id);

        // Delete interactions associated with the post.
        interactionRepository.deleteAll(
                interactionRepository.findByPostId(id)
        );
        interactionRepository.flush();

        postRepository.delete(post);
    }

    // GET POST WITH AUTHOR INFORMATION
    @Transactional(readOnly = true)
    public PostWithAuthorResponseDTO getPostWithAuthor(Long id) {
        Post post = findPost(id);

        return mapper.toPostWithAuthorResponseDTO(post);
    }

    // FIND POST OR THROW EXCEPTION
    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Post " + id + " not found"
                        )
                );
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
}
