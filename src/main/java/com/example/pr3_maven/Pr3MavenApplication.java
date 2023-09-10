package com.example.pr3_maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.pr3_maven.Repositories")
@EntityScan(basePackages = "com.example.pr3_maven.Models")
public class Pr3MavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pr3MavenApplication.class, args);
    }

}
