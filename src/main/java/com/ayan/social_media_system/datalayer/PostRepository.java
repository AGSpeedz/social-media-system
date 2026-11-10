package com.ayan.social_media_system.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthorId(Long authorId);
}
