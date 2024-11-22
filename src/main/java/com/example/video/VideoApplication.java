package com.example.video;

import com.example.video.dao.enteties.Creator;
import com.example.video.dao.enteties.Video;
import com.example.video.dao.repositories.CreatorRepository;
import com.example.video.dao.repositories.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CreatorRepository creatorRepository, VideoRepository videoRepository) {
        return args -> {
            List<Creator> creators = List.of(
                    Creator.builder().name("Creator1").email("C1@gmail.com").build(),
                    Creator.builder().name("Creator2").email("C2@gmail.com").build()
            );

            // Sauvegarde des créateurs
            creators.forEach(creatorRepository::save);

            // Création des vidéos avec des créateurs associés
            List<Video> videos = List.of(
                    Video.builder()
                            .name("Spring Boot ")
                            .url("http://spring-boot")
                            .description("Introduction  Spring Boot")
                            .datePublication("12/05/2024")
                            .creator(creators.get(0)) // Association avec John Doe
                            .build(),
                    Video.builder()
                            .name("GraphQL Basics")
                            .url("http://graphql")
                            .description("GraphQL")
                            .datePublication("15/02/2024")
                            .creator(creators.get(1)) // Association avec Jane Smith
                            .build()
            );

            // Sauvegarde des vidéos
            videos.forEach(videoRepository::save);
        };
    }
}
