package com.comunidev.comunidevbackend.category.adapter.out.mongodb;

import com.comunidev.comunidevbackend.category.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoCategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findById(String id);
    List<Category> findByUserIdOrderByOrdenAsc(String userId);
    void deleteById(String id);
}
