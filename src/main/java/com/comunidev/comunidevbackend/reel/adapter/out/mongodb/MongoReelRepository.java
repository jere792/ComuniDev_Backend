package com.comunidev.comunidevbackend.reel.adapter.out.mongodb;

import com.comunidev.comunidevbackend.reel.domain.Reel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoReelRepository extends MongoRepository<Reel, String> {
    List<Reel> findAllByOrderByCreatedAtDesc();
    List<Reel> findByAutorIdOrderByCreatedAtDesc(String autorId);
}
