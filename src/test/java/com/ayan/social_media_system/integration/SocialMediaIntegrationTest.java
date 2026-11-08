package com.ayan.social_media_system.integration;

import com.ayan.social_media_system.domain.User;
import com.ayan.social_media_system.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SocialMediaIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private InteractionRepository interactionRepository;

    @Test
    void databaseContainsSampleData() {

        assertEquals(10, userRepository.count());
        assertEquals(50, postRepository.count());
        assertEquals(50, interactionRepository.count());
    }

    @Test
    void userHasFivePosts() {

        User user = userRepository.findAll().get(0);

        assertEquals(
                5,
                postRepository.findByAuthorId(user.getId()).size()
        );
    }
}
