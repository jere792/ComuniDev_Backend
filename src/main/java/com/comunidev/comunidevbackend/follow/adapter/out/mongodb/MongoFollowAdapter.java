package com.comunidev.comunidevbackend.follow.adapter.out.mongodb;

import com.comunidev.comunidevbackend.follow.application.port.out.FollowRepositoryPort;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoFollowAdapter implements FollowRepositoryPort {

    private final MongoFollowRepository repository;

    @Override
    public Follow save(Follow follow) {
        return repository.save(follow);
    }

    @Override
    public List<Follow> findBySeguidorId(String seguidorId) {
        return repository.findBySeguidorId(seguidorId);
    }

    @Override
    public List<Follow> findBySeguidoId(String seguidoId) {
        return repository.findBySeguidoId(seguidoId);
    }

    @Override
    public Optional<Follow> findBySeguidorIdAndSeguidoId(String seguidorId, String seguidoId) {
        return repository.findBySeguidorIdAndSeguidoId(seguidorId, seguidoId);
    }

    @Override
    public void delete(Follow follow) {
        repository.delete(follow);
    }
}
