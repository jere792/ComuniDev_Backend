package com.comunidev.comunidevbackend.talent_search.adapter.in.rest;

import com.comunidev.comunidevbackend.shared.adapter.in.rest.dto.ApiResponse;
import com.comunidev.comunidevbackend.shared.adapter.in.rest.dto.PaginationResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SearchTalentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/talent-search")
@RequiredArgsConstructor
@Tag(name = "Talent Search", description = "Search for developer profiles")
public class TalentSearchController {

    private final SearchTalentUseCase searchTalentUseCase;

    @PostMapping("/search")
    @Operation(summary = "Search developers with filters")
    public ResponseEntity<ApiResponse<List<DeveloperProfileResponse>>> searchDevelopers(
            @RequestBody TalentSearchRequest request) {
        List<DeveloperProfileResponse> results = searchTalentUseCase.searchDevelopers(request);
        return ResponseEntity.ok(ApiResponse.ok(results));
    }
}
