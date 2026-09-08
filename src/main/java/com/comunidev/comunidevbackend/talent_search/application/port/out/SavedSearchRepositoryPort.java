package com.comunidev.comunidevbackend.talent_search.application.port.out;

import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;

import java.util.List;
import java.util.Optional;

public interface SavedSearchRepositoryPort {
    SavedSearch save(SavedSearch savedSearch);
    List<SavedSearch> findAllByRecruiterId(String recruiterId);
    Optional<SavedSearch> findById(String id);
    void deleteById(String id);
}
