package com.comunidev.comunidevbackend.talent_search.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.port.in.SearchTalentUseCase;
import com.comunidev.comunidevbackend.talent_search.application.port.out.DeveloperSearchPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class SearchTalentService implements SearchTalentUseCase {

    private final DeveloperSearchPort developerSearchPort;

    @Override
    public List<DeveloperProfileResponse> searchDevelopers(TalentSearchRequest request) {
        if (request == null) {
            request = new TalentSearchRequest();
        }
        return developerSearchPort.searchDevelopers(request);
    }
}
