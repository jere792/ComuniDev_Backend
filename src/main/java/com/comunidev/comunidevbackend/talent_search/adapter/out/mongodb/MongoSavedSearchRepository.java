package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoSavedSearchRepository extends MongoRepository<SavedSearch, String> {
    List<SavedSearch> findAllByRecruiterId(String recruiterId);
}
