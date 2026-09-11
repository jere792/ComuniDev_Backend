package com.comunidev.comunidevbackend.developer_profile.adapter.out.mongodb;

import com.comunidev.comunidevbackend.developer_profile.application.port.out.DeveloperProfileRepositoryPort;
import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoDeveloperProfileAdapter implements DeveloperProfileRepositoryPort {

    private final MongoDeveloperProfileRepository repository;

    @Override
    public DeveloperProfile save(DeveloperProfile profile) {
        return repository.save(profile);
    }

    @Override
    public Optional<DeveloperProfile> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }
}
