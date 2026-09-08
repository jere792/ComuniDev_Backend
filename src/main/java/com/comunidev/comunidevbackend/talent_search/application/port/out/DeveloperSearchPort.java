package com.comunidev.comunidevbackend.talent_search.application.port.out;

import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;

import java.util.List;

public interface DeveloperSearchPort {
    List<DeveloperProfileResponse> searchDevelopers(TalentSearchRequest request);
}
