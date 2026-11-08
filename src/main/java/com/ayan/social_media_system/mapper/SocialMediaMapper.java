package com.ayan.social_media_system.mapper;

import com.ayan.social_media_system.domain.*;
import com.ayan.social_media_system.presentation.request.*;
import com.ayan.social_media_system.presentation.response.*;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SocialMediaMapper {


    public UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName()
        );
    }

    public User toUserEntity(UserRequestDTO request) {
        User user = new User();
        updateUserEntity(user, request);
        return user;
    }

    public void updateUserEntity(User user, UserRequestDTO request) {
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
    }



    public PostResponseDTO toPostResponseDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getAuthor().getId(),
                post.getAuthor().getUsername(),
                post.getContent(),
                post.getMediaUrl(),
                post.getCreatedAt()
        );
    }

    public Post toPostEntity(PostRequestDTO request, User author) {
        Post post = new Post();
        updatePostEntity(post, request, author);
        return post;
    }

    public void updatePostEntity(
            Post post,
            PostRequestDTO request,
            User author
    ) {
        post.setContent(request.content());
        post.setMediaUrl(request.mediaUrl());
        post.setAuthor(author);
    }



    public InteractionResponseDTO toInteractionResponseDTO(
            Interaction interaction
    ) {
        return new InteractionResponseDTO(
                interaction.getId(),
                interaction.getUser().getId(),
                interaction.getUser().getUsername(),
                interaction.getPost().getId(),
                interaction.getType(),
                interaction.getCommentText(),
                interaction.getCreatedAt()
        );
    }

    public Interaction toInteractionEntity(
            InteractionRequestDTO request,
            User user,
            Post post
    ) {
        Interaction interaction = new Interaction();
        updateInteractionEntity(interaction, request, user, post);
        return interaction;
    }

    public void updateInteractionEntity(
            Interaction interaction,
            InteractionRequestDTO request,
            User user,
            Post post
    ) {
        interaction.setType(request.type());
        interaction.setCommentText(request.commentText());
        interaction.setUser(user);
        interaction.setPost(post);
    }



    public UserWithPostsResponseDTO toUserWithPostsResponseDTO(
            User user,
            List<Post> posts
    ) {
        List<PostResponseDTO> postResponses = posts.stream()
                .map(this::toPostResponseDTO)
                .toList();

        return new UserWithPostsResponseDTO(
                toUserResponseDTO(user),
                postResponses
        );
    }

    public PostWithAuthorResponseDTO toPostWithAuthorResponseDTO(
            Post post
    ) {
        return new PostWithAuthorResponseDTO(
                toPostResponseDTO(post),
                toUserResponseDTO(post.getAuthor())
        );
    }
}
