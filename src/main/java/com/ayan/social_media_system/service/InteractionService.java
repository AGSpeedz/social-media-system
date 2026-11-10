package com.ayan.social_media_system.service;


import com.ayan.social_media_system.datalayer.Interaction;
import com.ayan.social_media_system.datalayer.InteractionType;
import com.ayan.social_media_system.datalayer.Post;
import com.ayan.social_media_system.datalayer.User;
import com.ayan.social_media_system.exception.NotFoundException;
import com.ayan.social_media_system.mapper.SocialMediaMapper;
import com.ayan.social_media_system.presentation.request.InteractionRequestDTO;
import com.ayan.social_media_system.presentation.response.InteractionResponseDTO;
import com.ayan.social_media_system.datalayer.InteractionRepository;
import com.ayan.social_media_system.datalayer.PostRepository;
import com.ayan.social_media_system.datalayer.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SocialMediaMapper mapper;

    public InteractionService(
            InteractionRepository interactionRepository,
            UserRepository userRepository,
            PostRepository postRepository,
            SocialMediaMapper mapper
    ) {
        this.interactionRepository = interactionRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    // GET ALL INTERACTIONS
    @Transactional(readOnly = true)
    public List<InteractionResponseDTO> getAllInteractions() {
        return interactionRepository.findAll()
                .stream()
                .map(mapper::toInteractionResponseDTO)
                .toList();
    }

    // GET INTERACTION BY ID
    @Transactional(readOnly = true)
    public InteractionResponseDTO getInteractionById(Long id) {
        Interaction interaction = findInteraction(id);
        return mapper.toInteractionResponseDTO(interaction);
    }

    // CREATE INTERACTION
    public InteractionResponseDTO createInteraction(
            InteractionRequestDTO request
    ) {
        validateInteraction(request);

        User user = findUser(request.getUserId());
        Post post = findPost(request.getPostId());

        Interaction interaction =
                mapper.toInteractionEntity(request, user, post);

        Interaction savedInteraction =
                interactionRepository.save(interaction);

        return mapper.toInteractionResponseDTO(savedInteraction);
    }

    // UPDATE INTERACTION
    public InteractionResponseDTO updateInteraction(
            Long id,
            InteractionRequestDTO request
    ) {
        Interaction interaction = findInteraction(id);

        validateInteraction(request);

        User user = findUser(request.getUserId());
        Post post = findPost(request.getPostId());

        mapper.updateInteractionEntity(
                interaction,
                request,
                user,
                post
        );

        Interaction savedInteraction =
                interactionRepository.save(interaction);

        return mapper.toInteractionResponseDTO(savedInteraction);
    }

    // DELETE INTERACTION
    public void deleteInteraction(Long id) {
        Interaction interaction = findInteraction(id);
        interactionRepository.delete(interaction);
    }

    // GET INTERACTIONS FOR A POST
    @Transactional(readOnly = true)
    public List<InteractionResponseDTO> getInteractionsByPostId(
            Long postId
    ) {
        findPost(postId);

        return interactionRepository.findByPostId(postId)
                .stream()
                .map(mapper::toInteractionResponseDTO)
                .toList();
    }

    // GET INTERACTIONS BY A USER
    @Transactional(readOnly = true)
    public List<InteractionResponseDTO> getInteractionsByUserId(
            Long userId
    ) {
        findUser(userId);

        return interactionRepository.findByUserId(userId)
                .stream()
                .map(mapper::toInteractionResponseDTO)
                .toList();
    }

    // VALIDATE INTERACTION
    private void validateInteraction(InteractionRequestDTO request) {

        if (request.getType() == InteractionType.COMMENT) {

            if (request.getCommentText() == null ||
                    request.getCommentText().isBlank()) {

                throw new IllegalArgumentException(
                        "Comment text is required for COMMENT interactions"
                );
            }
        }
    }

    // FIND INTERACTION
    private Interaction findInteraction(Long id) {
        return interactionRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Interaction " + id + " not found"
                        )
                );
    }

    // FIND USER
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "User " + id + " not found"
                        )
                );
    }

    // FIND POST
    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Post " + id + " not found"
                        )
                );
    }
}
