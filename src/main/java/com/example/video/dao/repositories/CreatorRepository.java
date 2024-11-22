package com.example.video.dao.repositories;

import com.example.video.dao.enteties.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorRepository extends JpaRepository<Creator,Integer> {
    Optional<Creator> findByEmail(String email);

}
