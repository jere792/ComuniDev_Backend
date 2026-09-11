package com.comunidev.comunidevbackend.developer_profile.application.port.out;

import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;

import java.util.Optional;

public interface DeveloperProfileRepositoryPort {
    DeveloperProfile save(DeveloperProfile profile);
    Optional<DeveloperProfile> findByUserId(String userId);
    void deleteByUserId(String userId);
}
