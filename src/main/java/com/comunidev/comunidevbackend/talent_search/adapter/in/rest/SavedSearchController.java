package com.comunidev.comunidevbackend.talent_search.adapter.in.rest;

import com.comunidev.comunidevbackend.shared.adapter.in.rest.dto.ApiResponse;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SavedSearchUseCase;
import com.comunidev.comunidevbackend.talent_search.domain.SavedSearch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/talent-search/saved-searches")
@RequiredArgsConstructor
@Tag(name = "Saved Searches", description = "Manage saved search queries")
public class SavedSearchController {

    private final SavedSearchUseCase savedSearchUseCase;

    @PostMapping
    @Operation(summary = "Save a search query")
    public ResponseEntity<ApiResponse<SavedSearch>> saveSearch(
            @RequestParam String recruiterId,
            @RequestParam String name,
            @RequestParam(required = false) List<String> technologies,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) String locationCountry,
            @RequestParam(required = false) String locationCity,
            @RequestParam(required = false) Boolean remoteAvailable,
            @RequestParam(required = false) String availability,
            @RequestParam(required = false) List<String> previousExperience) {
        SavedSearch saved = savedSearchUseCase.saveSearch(
                recruiterId, name, technologies, experienceLevel,
                locationCountry, locationCity, remoteAvailable,
                availability, previousExperience
        );
        return ResponseEntity.ok(ApiResponse.ok("Search saved successfully", saved));
    }

    @GetMapping
    @Operation(summary = "Get all saved searches for a recruiter")
    public ResponseEntity<ApiResponse<List<SavedSearch>>> getSavedSearches(
            @RequestParam String recruiterId) {
        List<SavedSearch> searches = savedSearchUseCase.getSavedSearches(recruiterId);
        return ResponseEntity.ok(ApiResponse.ok(searches));
    }

    @DeleteMapping("/{savedSearchId}")
    @Operation(summary = "Delete a saved search")
    public ResponseEntity<ApiResponse<Void>> deleteSavedSearch(
            @PathVariable String savedSearchId,
            @RequestParam String recruiterId) {
        savedSearchUseCase.deleteSavedSearch(recruiterId, savedSearchId);
        return ResponseEntity.ok(ApiResponse.ok("Search deleted successfully", null));
    }
}
