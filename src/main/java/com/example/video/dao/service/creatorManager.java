package com.example.video.dao.service;

import com.example.video.dao.dto.CreatorRequest;
import com.example.video.dao.enteties.Creator;
import com.example.video.dao.repositories.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class creatorManager {

    @Autowired
    private CreatorRepository creatorRepository; // Injection de CreatorRepository

    // Méthode pour sauvegarder un créateur
    public Creator saveCreator(CreatorRequest creatorRequest) {
        // Transformer CreatorRequest en un objet Creator
        Creator creator = new Creator();
        creator.setName(creatorRequest.getName());
        creator.setEmail(creatorRequest.getEmail());

        // Sauvegarder le créateur dans la base de données
        return creatorRepository.save(creator);
    }
}
