package com.comunidev.comunidevbackend.recruiter_profile.adapter.out.mongodb;

import com.comunidev.comunidevbackend.recruiter_profile.application.port.out.RecruiterProfileRepositoryPort;
import com.comunidev.comunidevbackend.recruiter_profile.domain.RecruiterProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoRecruiterProfileAdapter implements RecruiterProfileRepositoryPort {

    private final MongoRecruiterProfileRepository repository;

    @Override
    public RecruiterProfile save(RecruiterProfile profile) {
        return repository.save(profile);
    }

    @Override
    public Optional<RecruiterProfile> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }
}
