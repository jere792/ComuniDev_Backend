package com.comunidev.comunidevbackend.talent_search.application.port.in;

import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;

import java.util.List;

public interface SearchTalentUseCase {
    List<DeveloperProfileResponse> searchDevelopers(TalentSearchRequest request);
}
