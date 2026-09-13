package com.comunidev.comunidevbackend.follow.application.port.out;

import com.comunidev.comunidevbackend.follow.domain.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepositoryPort {
    Follow save(Follow follow);
    List<Follow> findBySeguidorId(String seguidorId);
    List<Follow> findBySeguidoId(String seguidoId);
    Optional<Follow> findBySeguidorIdAndSeguidoId(String seguidorId, String seguidoId);
    void delete(Follow follow);
}
