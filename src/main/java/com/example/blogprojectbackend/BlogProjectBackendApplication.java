package com.example.blogprojectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlogProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogProjectBackendApplication.class, args);
    }

}
