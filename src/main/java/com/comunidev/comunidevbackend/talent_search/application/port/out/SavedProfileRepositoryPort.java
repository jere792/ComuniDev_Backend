package com.comunidev.comunidevbackend.talent_search.application.port.out;

import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;

import java.util.List;
import java.util.Optional;

public interface SavedProfileRepositoryPort {
    SavedProfile save(SavedProfile savedProfile);
    void deleteByRecruiterIdAndDeveloperId(String recruiterId, String developerId);
    Optional<SavedProfile> findByRecruiterIdAndDeveloperId(String recruiterId, String developerId);
    List<SavedProfile> findAllByRecruiterId(String recruiterId);
}
