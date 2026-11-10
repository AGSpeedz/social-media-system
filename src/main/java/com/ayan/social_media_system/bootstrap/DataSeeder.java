package com.ayan.social_media_system.bootstrap;

import com.ayan.social_media_system.datalayer.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final InteractionRepository interactionRepository;

    public DataSeeder(
            UserRepository userRepository,
            PostRepository postRepository,
            InteractionRepository interactionRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.interactionRepository = interactionRepository;
    }

    @Override
    public void run(String... args) {

        // Prevent duplicate sample data.
        if (userRepository.count() > 0) {
            System.out.println("Database already contains users. Skipping seed.");
            return;
        }

        // STEP 1: CREATE 10 USERS
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            User user = new User();

            user.setUsername("socialuser" + i);
            user.setEmail("user" + i + "@example.com");
            user.setDisplayName("Social User " + i);

            users.add(user);
        }

        List<User> savedUsers = userRepository.saveAll(users);

        System.out.println("Created 10 sample users.");

        // STEP 2: CREATE 5 POSTS FOR EACH USER
        List<Post> posts = new ArrayList<>();

        for (User user : savedUsers) {
            for (int i = 1; i <= 5; i++) {
                Post post = new Post();

                post.setContent(
                        "Sample post " + i + " by " + user.getUsername()
                );

                if (i % 2 == 0) {
                    post.setMediaUrl(
                            "https://example.com/images/sample" + i + ".jpg"
                    );
                }

                post.setAuthor(user);

                posts.add(post);
            }
        }

        List<Post> savedPosts = postRepository.saveAll(posts);

        System.out.println("Created 50 sample posts.");

        // STEP 3: CREATE 50 INTERACTIONS
        List<Interaction> interactions = new ArrayList<>();

        for (int i = 0; i < savedPosts.size(); i++) {

            Interaction interaction = new Interaction();

            Post post = savedPosts.get(i);

            // Another user interacts with the post.
            int authorIndex = i / 5;
            int interactingUserIndex =
                    (authorIndex + 1) % savedUsers.size();

            User interactingUser =
                    savedUsers.get(interactingUserIndex);

            interaction.setUser(interactingUser);
            interaction.setPost(post);

            // Alternate between likes and comments.
            if (i % 2 == 0) {
                interaction.setType(InteractionType.LIKE);
            } else {
                interaction.setType(InteractionType.COMMENT);
                interaction.setCommentText(
                        "Great post! Sample comment " + (i + 1)
                );
            }

            interactions.add(interaction);
        }

        interactionRepository.saveAll(interactions);

        System.out.println("Created 50 sample interactions.");
        System.out.println("Social Media database seeding completed!");
    }
}
