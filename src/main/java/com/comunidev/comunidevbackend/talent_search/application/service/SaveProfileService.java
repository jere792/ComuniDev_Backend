package com.comunidev.comunidevbackend.talent_search.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SaveProfileUseCase;
import com.comunidev.comunidevbackend.talent_search.application.port.out.SavedProfileRepositoryPort;
import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class SaveProfileService implements SaveProfileUseCase {

    private final SavedProfileRepositoryPort savedProfileRepositoryPort;

    @Override
    public SavedProfile saveProfile(String recruiterId, String developerId, String notes) {
        if (recruiterId == null || developerId == null) {
            throw new BusinessException("Recruiter ID and Developer ID are required");
        }

        if (recruiterId.equals(developerId)) {
            throw new BusinessException("You cannot save your own profile");
        }

        savedProfileRepositoryPort.findByRecruiterIdAndDeveloperId(recruiterId, developerId)
                .ifPresent(existing -> {
                    throw new BusinessException("Profile already saved");
                });

        SavedProfile savedProfile = SavedProfile.create(recruiterId, developerId, notes);
        return savedProfileRepositoryPort.save(savedProfile);
    }

    @Override
    public void unsaveProfile(String recruiterId, String developerId) {
        savedProfileRepositoryPort.findByRecruiterIdAndDeveloperId(recruiterId, developerId)
                .orElseThrow(() -> new BusinessException("Saved profile not found"));

        savedProfileRepositoryPort.deleteByRecruiterIdAndDeveloperId(recruiterId, developerId);
    }

    @Override
    public boolean isProfileSaved(String recruiterId, String developerId) {
        return savedProfileRepositoryPort.findByRecruiterIdAndDeveloperId(recruiterId, developerId)
                .isPresent();
    }
}
