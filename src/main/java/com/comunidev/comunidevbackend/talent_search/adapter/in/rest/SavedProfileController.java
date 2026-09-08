package com.comunidev.comunidevbackend.talent_search.adapter.in.rest;

import com.comunidev.comunidevbackend.shared.adapter.in.rest.dto.ApiResponse;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SaveProfileUseCase;
import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/talent-search/saved-profiles")
@RequiredArgsConstructor
@Tag(name = "Saved Profiles", description = "Manage saved developer profiles")
public class SavedProfileController {

    private final SaveProfileUseCase saveProfileUseCase;

    @PostMapping("/{developerId}")
    @Operation(summary = "Save a developer profile")
    public ResponseEntity<ApiResponse<SavedProfile>> saveProfile(
            @PathVariable String developerId,
            @RequestParam String recruiterId,
            @RequestParam(required = false) String notes) {
        SavedProfile saved = saveProfileUseCase.saveProfile(recruiterId, developerId, notes);
        return ResponseEntity.ok(ApiResponse.ok("Profile saved successfully", saved));
    }

    @DeleteMapping("/{developerId}")
    @Operation(summary = "Unsave a developer profile")
    public ResponseEntity<ApiResponse<Void>> unsaveProfile(
            @PathVariable String developerId,
            @RequestParam String recruiterId) {
        saveProfileUseCase.unsaveProfile(recruiterId, developerId);
        return ResponseEntity.ok(ApiResponse.ok("Profile unsaved successfully", null));
    }

    @GetMapping("/{developerId}/check")
    @Operation(summary = "Check if a developer profile is saved")
    public ResponseEntity<ApiResponse<Boolean>> isProfileSaved(
            @PathVariable String developerId,
            @RequestParam String recruiterId) {
        boolean isSaved = saveProfileUseCase.isProfileSaved(recruiterId, developerId);
        return ResponseEntity.ok(ApiResponse.ok(isSaved));
    }
}
