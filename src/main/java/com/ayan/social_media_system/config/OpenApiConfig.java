package com.ayan.social_media_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI socialMediaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Social Media System API")
                        .description(
                                "REST API for managing users, posts, " +
                                        "likes, and comments."
                        )
                        .version("1.0.0"));
    }
}
