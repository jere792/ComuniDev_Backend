package com.comunidev.comunidevbackend.block.adapter.in.graphql;

import com.comunidev.comunidevbackend.block.application.port.out.BlockRepositoryPort;
import com.comunidev.comunidevbackend.block.domain.Block;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BlockGraphQLResolver {

    private final BlockRepositoryPort blockRepositoryPort;

    @QueryMapping
    public Boolean isBlocked(@Argument String bloqueadorId, @Argument String bloqueadoId) {
        return blockRepositoryPort.findByBloqueadorIdAndBloqueadoId(bloqueadorId, bloqueadoId).isPresent();
    }

    @MutationMapping
    public Block block(@Argument String bloqueadorId, @Argument String bloqueadoId) {
        return blockRepositoryPort.findByBloqueadorIdAndBloqueadoId(bloqueadorId, bloqueadoId)
                .orElseGet(() -> blockRepositoryPort.save(Block.create(bloqueadorId, bloqueadoId)));
    }

    @MutationMapping
    public Boolean unblock(@Argument String bloqueadorId, @Argument String bloqueadoId) {
        return blockRepositoryPort.findByBloqueadorIdAndBloqueadoId(bloqueadorId, bloqueadoId)
                .map(block -> {
                    blockRepositoryPort.delete(block);
                    return true;
                })
                .orElse(false);
    }
}
