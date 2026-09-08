package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoSavedProfileRepository extends MongoRepository<SavedProfile, String> {
    void deleteByRecruiterIdAndDeveloperId(String recruiterId, String developerId);
    Optional<SavedProfile> findByRecruiterIdAndDeveloperId(String recruiterId, String developerId);
    List<SavedProfile> findAllByRecruiterId(String recruiterId);
}
