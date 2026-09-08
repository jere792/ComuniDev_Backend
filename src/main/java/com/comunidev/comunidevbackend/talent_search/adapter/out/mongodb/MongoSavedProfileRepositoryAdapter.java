package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.application.port.out.SavedProfileRepositoryPort;
import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoSavedProfileRepositoryAdapter implements SavedProfileRepositoryPort {

    private final MongoSavedProfileRepository repository;

    @Override
    public SavedProfile save(SavedProfile savedProfile) {
        return repository.save(savedProfile);
    }

    @Override
    public void deleteByRecruiterIdAndDeveloperId(String recruiterId, String developerId) {
        repository.deleteByRecruiterIdAndDeveloperId(recruiterId, developerId);
    }

    @Override
    public Optional<SavedProfile> findByRecruiterIdAndDeveloperId(String recruiterId, String developerId) {
        return repository.findByRecruiterIdAndDeveloperId(recruiterId, developerId);
    }

    @Override
    public List<SavedProfile> findAllByRecruiterId(String recruiterId) {
        return repository.findAllByRecruiterId(recruiterId);
    }
}
