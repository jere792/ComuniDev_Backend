package com.comunidev.comunidevbackend.follow.adapter.in.graphql;

import com.comunidev.comunidevbackend.follow.application.port.out.FollowRepositoryPort;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FollowGraphQLResolver {

    private final FollowRepositoryPort followRepositoryPort;

    @QueryMapping
    public List<Follow> followers(@Argument String userId) {
        return followRepositoryPort.findBySeguidoId(userId);
    }

    @QueryMapping
    public List<Follow> following(@Argument String userId) {
        return followRepositoryPort.findBySeguidorId(userId);
    }

    @QueryMapping
    public Boolean isFollowing(@Argument String followerId, @Argument String followedId) {
        return followRepositoryPort.findBySeguidorIdAndSeguidoId(followerId, followedId).isPresent();
    }

    @MutationMapping
    public Follow follow(
            @Argument String seguidorId,
            @Argument String tipoSeguido,
            @Argument String seguidoId) {

        return followRepositoryPort.findBySeguidorIdAndSeguidoId(seguidorId, seguidoId)
                .orElseGet(() -> followRepositoryPort.save(Follow.create(seguidorId, tipoSeguido, seguidoId)));
    }

    @MutationMapping
    public Boolean unfollow(
            @Argument String seguidorId,
            @Argument String tipoSeguido,
            @Argument String seguidoId) {

        return followRepositoryPort.findBySeguidorIdAndSeguidoId(seguidorId, seguidoId)
                .map(follow -> {
                    followRepositoryPort.delete(follow);
                    return true;
                })
                .orElse(false);
    }
}
