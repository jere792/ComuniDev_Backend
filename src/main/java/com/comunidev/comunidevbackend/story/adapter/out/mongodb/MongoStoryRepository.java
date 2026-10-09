package com.comunidev.comunidevbackend.story.adapter.out.mongodb;

import com.comunidev.comunidevbackend.story.domain.Story;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface MongoStoryRepository extends MongoRepository<Story, String> {
    List<Story> findByAutorIdInAndCreatedAtAfterOrderByCreatedAtDesc(List<String> autorIds, Instant since);
    List<Story> findByAutorIdOrderByCreatedAtDesc(String autorId);
    List<Story> findByAutorIdAndCategoriaIdsContainingOrderByCreatedAtDesc(String autorId, String categoriaId);
    List<Story> findByCategoriaIdsContaining(String categoriaId);
}
