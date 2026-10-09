package com.comunidev.comunidevbackend.category.adapter.out.mongodb;

import com.comunidev.comunidevbackend.category.application.port.out.CategoryRepositoryPort;
import com.comunidev.comunidevbackend.category.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoCategoryAdapter implements CategoryRepositoryPort {

    private final MongoCategoryRepository repository;

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Optional<Category> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> findByUserIdOrderByOrdenAsc(String userId) {
        return repository.findByUserIdOrderByOrdenAsc(userId);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
