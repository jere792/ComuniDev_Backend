package com.comunidev.comunidevbackend.follow.adapter.out.mongodb;

import com.comunidev.comunidevbackend.follow.domain.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoFollowRepository extends MongoRepository<Follow, String> {
    List<Follow> findBySeguidorId(String seguidorId);
    List<Follow> findBySeguidoId(String seguidoId);
    Optional<Follow> findBySeguidorIdAndSeguidoId(String seguidorId, String seguidoId);
}
