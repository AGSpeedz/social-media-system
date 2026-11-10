package com.ayan.social_media_system.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findByUserId(Long userId);

    List<Interaction> findByPostId(Long postId);
}
