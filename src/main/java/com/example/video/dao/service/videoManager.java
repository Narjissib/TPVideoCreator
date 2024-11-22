package com.example.video.dao.service;

import com.example.video.dao.enteties.Video;
import com.example.video.dao.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class videoManager {

    @Autowired
    private VideoRepository videoRepository;  // Assurez-vous que vous avez un repository pour gérer les vidéos.

    // Méthode pour trouver une vidéo par son ID
    public Video findById(Integer id) {
        return videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found"));
    }

    // Méthode pour mettre à jour une vidéo
    public Video updateVideo(Video video) {
        return videoRepository.save(video);  // Utilise save pour mettre à jour ou insérer la vidéo
    }
}