package com.comunidev.comunidevbackend.recruiter_profile.adapter.out.mongodb;

import com.comunidev.comunidevbackend.recruiter_profile.domain.RecruiterProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoRecruiterProfileRepository extends MongoRepository<RecruiterProfile, String> {
    Optional<RecruiterProfile> findByUserId(String userId);
    void deleteByUserId(String userId);
}
