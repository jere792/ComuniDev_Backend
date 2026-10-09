package com.comunidev.comunidevbackend.category.application.port.out;

import com.comunidev.comunidevbackend.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(String id);
    List<Category> findByUserIdOrderByOrdenAsc(String userId);
    void deleteById(String id);
}
