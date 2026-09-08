package com.comunidev.comunidevbackend.talent_search.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SavedSearchUseCase;
import com.comunidev.comunidevbackend.talent_search.application.port.out.SavedSearchRepositoryPort;
import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class SavedSearchService implements SavedSearchUseCase {

    private final SavedSearchRepositoryPort savedSearchRepositoryPort;

    @Override
    public SavedSearch saveSearch(String recruiterId, String name, List<String> technologies,
                                  String experienceLevel, String locationCountry, String locationCity,
                                  Boolean remoteAvailable, String availability, List<String> previousExperience) {
        if (recruiterId == null || name == null || name.isBlank()) {
            throw new BusinessException("Recruiter ID and search name are required");
        }

        SavedSearch savedSearch = SavedSearch.create(
                recruiterId, name, technologies, experienceLevel,
                locationCountry, locationCity, remoteAvailable,
                availability, previousExperience
        );
        return savedSearchRepositoryPort.save(savedSearch);
    }

    @Override
    public List<SavedSearch> getSavedSearches(String recruiterId) {
        if (recruiterId == null) {
            throw new BusinessException("Recruiter ID is required");
        }
        return savedSearchRepositoryPort.findAllByRecruiterId(recruiterId);
    }

    @Override
    public void deleteSavedSearch(String recruiterId, String savedSearchId) {
        SavedSearch savedSearch = savedSearchRepositoryPort.findById(savedSearchId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedSearch", "id", savedSearchId));

        if (!savedSearch.getRecruiterId().equals(recruiterId)) {
            throw new BusinessException("You can only delete your own saved searches");
        }

        savedSearchRepositoryPort.deleteById(savedSearchId);
    }
}
