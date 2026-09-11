package com.comunidev.comunidevbackend.developer_profile.adapter.out.mongodb;

import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoDeveloperProfileRepository extends MongoRepository<DeveloperProfile, String> {
    Optional<DeveloperProfile> findByUserId(String userId);
    void deleteByUserId(String userId);
}
