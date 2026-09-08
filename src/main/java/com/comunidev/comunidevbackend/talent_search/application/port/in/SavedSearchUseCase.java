package com.comunidev.comunidevbackend.talent_search.application.port.in;

import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;

import java.util.List;

public interface SavedSearchUseCase {
    SavedSearch saveSearch(String recruiterId, String name, List<String> technologies,
                           String experienceLevel, String locationCountry, String locationCity,
                           Boolean remoteAvailable, String availability, List<String> previousExperience);
    List<SavedSearch> getSavedSearches(String recruiterId);
    void deleteSavedSearch(String recruiterId, String savedSearchId);
}
