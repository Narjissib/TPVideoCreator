package com.example.video.dao.web;

import com.example.video.dao.dto.CreatorRequest;
import com.example.video.dao.dto.VideoRequest;
import com.example.video.dao.enteties.Creator;
import com.example.video.dao.enteties.Video;
import com.example.video.dao.repositories.CreatorRepository;
import com.example.video.dao.repositories.VideoRepository;
import com.example.video.dao.service.creatorManager;
import com.example.video.dao.service.videoManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class VideoGraphQlController {
    private final CreatorRepository creatorRepository;
    private final VideoRepository videoRepository;
    @Autowired
    private videoManager videomanager;   // Injection de VideoManager
    @Autowired
    private creatorManager creatormanager; // Injection de CreatorManager

    @QueryMapping
    public List<Video> videoList(){
        return videoRepository.findAll();
    }
    @QueryMapping
    public Creator creatorById(@Argument Integer id) {
        return creatorRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Creator %s not found",id)));
    }
    @MutationMapping
    public Creator saveCreator(@Argument CreatorRequest creator){
        Creator creator1 = Creator.builder()
                .name(creator.getName())
                .email(creator.getEmail())
                .build();
        return creatorRepository.save(creator1);
    }
    @MutationMapping
    public Video saveVideo(@Argument VideoRequest video) {
        System.out.println("VideoRequest: " + video);
        System.out.println("CreatorRequest: " + video.getCreator());

        if (video.getCreator() == null) {
            throw new RuntimeException("CreatorRequest is required!!!!!");
        }

        Creator creator;
        Optional<Creator> existingCreator = creatorRepository.findByEmail(video.getCreator().getEmail());
        if (existingCreator.isPresent()) {
            creator = existingCreator.get();
        } else {
            creator = Creator.builder()
                    .name(video.getCreator().getName())
                    .email(video.getCreator().getEmail())
                    .build();
            creator = creatorRepository.save(creator);
        }

        Video newVideo = Video.builder()
                .name(video.getName())
                .url(video.getUrl())
                .description(video.getDescription())
                .datePublication(video.getDatePublication())
                .creator(creator)
                .build();

        return videoRepository.save(newVideo);
    }
    @SubscriptionMapping
    public Flux<Video> notifyVideoChange() {
        return Flux.fromStream(
                Stream.generate(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Random random = new Random();
                    CreatorRequest creatorRequest = CreatorRequest.builder().name("x" +
                                    new Random().nextInt())
                            .email("x@gmail.com").build();
                    Creator creator = creatormanager.saveCreator(creatorRequest);
                    Video video = videomanager.findById(1);
                    video.setCreator(creator);
                    videomanager.updateVideo(video);
                    return video;
                }));
    }
}
