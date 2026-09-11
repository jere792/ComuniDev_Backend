package com.comunidev.comunidevbackend.talent_search.adapter.in.graphql;

import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SaveProfileUseCase;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SavedSearchUseCase;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SearchTalentUseCase;
import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;
import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TalentSearchGraphQLResolver {

    private final SearchTalentUseCase searchTalentUseCase;
    private final SaveProfileUseCase saveProfileUseCase;
    private final SavedSearchUseCase savedSearchUseCase;

    @QueryMapping
    public List<DeveloperProfileResponse> searchDevelopers(
            @Argument List<String> technologies,
            @Argument String experienceLevel,
            @Argument String locationCountry,
            @Argument String locationCity,
            @Argument Boolean remoteAvailable,
            @Argument String availability,
            @Argument List<String> previousExperience,
            @Argument String sortBy,
            @Argument String sortOrder,
            @Argument Integer page,
            @Argument Integer size) {

        TalentSearchRequest request = new TalentSearchRequest();
        request.setTechnologies(technologies);
        request.setExperienceLevel(experienceLevel);
        request.setLocationCountry(locationCountry);
        request.setLocationCity(locationCity);
        request.setRemoteAvailable(remoteAvailable);
        request.setAvailability(availability);
        request.setPreviousExperience(previousExperience);
        request.setSortBy(sortBy);
        request.setSortOrder(sortOrder);
        request.setPage(page);
        request.setSize(size);

        return searchTalentUseCase.searchDevelopers(request);
    }

    @QueryMapping
    public List<SavedProfile> savedProfiles(@Argument String recruiterId) {
        return saveProfileUseCase.getSavedProfiles(recruiterId);
    }

    @QueryMapping
    public Boolean isProfileSaved(@Argument String recruiterId, @Argument String developerId) {
        return saveProfileUseCase.isProfileSaved(recruiterId, developerId);
    }

    @QueryMapping
    public List<SavedSearch> savedSearches(@Argument String recruiterId) {
        return savedSearchUseCase.getSavedSearches(recruiterId);
    }

    @MutationMapping
    public SavedProfile saveDeveloperProfile(
            @Argument String recruiterId,
            @Argument String developerId,
            @Argument String notes) {
        return saveProfileUseCase.saveProfile(recruiterId, developerId, notes);
    }

    @MutationMapping
    public Boolean unsaveDeveloperProfile(
            @Argument String recruiterId,
            @Argument String developerId) {
        saveProfileUseCase.unsaveProfile(recruiterId, developerId);
        return true;
    }

    @MutationMapping
    public SavedSearch saveSearch(
            @Argument String recruiterId,
            @Argument String name,
            @Argument List<String> technologies,
            @Argument String experienceLevel,
            @Argument String locationCountry,
            @Argument String locationCity,
            @Argument Boolean remoteAvailable,
            @Argument String availability,
            @Argument List<String> previousExperience) {
        return savedSearchUseCase.saveSearch(
                recruiterId, name, technologies, experienceLevel,
                locationCountry, locationCity, remoteAvailable,
                availability, previousExperience);
    }

    @MutationMapping
    public Boolean deleteSavedSearch(@Argument String savedSearchId) {
        savedSearchUseCase.deleteSavedSearch(null, savedSearchId);
        return true;
    }
}
