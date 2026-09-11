package com.comunidev.comunidevbackend.recruiter_profile.application.port.out;

import com.comunidev.comunidevbackend.recruiter_profile.domain.RecruiterProfile;

import java.util.Optional;

public interface RecruiterProfileRepositoryPort {
    RecruiterProfile save(RecruiterProfile profile);
    Optional<RecruiterProfile> findByUserId(String userId);
    void deleteByUserId(String userId);
}
