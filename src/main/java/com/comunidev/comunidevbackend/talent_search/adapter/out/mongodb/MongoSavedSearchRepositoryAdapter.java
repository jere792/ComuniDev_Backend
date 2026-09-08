package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.application.port.out.SavedSearchRepositoryPort;
import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoSavedSearchRepositoryAdapter implements SavedSearchRepositoryPort {

    private final MongoSavedSearchRepository repository;

    @Override
    public SavedSearch save(SavedSearch savedSearch) {
        return repository.save(savedSearch);
    }

    @Override
    public List<SavedSearch> findAllByRecruiterId(String recruiterId) {
        return repository.findAllByRecruiterId(recruiterId);
    }

    @Override
    public Optional<SavedSearch> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
