package com.example.video.dao.repositories;

import com.example.video.dao.enteties.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Integer> {

}
