package com.comunidev.comunidevbackend.story.adapter.out.mongodb;

import com.comunidev.comunidevbackend.story.domain.Story;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface MongoStoryRepository extends MongoRepository<Story, String> {
    List<Story> findByAutorIdAndFechaExpiracionAfterOrderByCreatedAtAsc(String autorId, Instant now);
    List<Story> findByAutorIdInAndFechaExpiracionAfterOrderByCreatedAtAsc(List<String> autorIds, Instant now);
}
