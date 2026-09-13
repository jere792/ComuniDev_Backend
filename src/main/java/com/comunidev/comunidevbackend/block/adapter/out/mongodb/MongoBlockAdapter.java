package com.comunidev.comunidevbackend.block.adapter.out.mongodb;

import com.comunidev.comunidevbackend.block.application.port.out.BlockRepositoryPort;
import com.comunidev.comunidevbackend.block.domain.Block;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoBlockAdapter implements BlockRepositoryPort {

    private final MongoBlockRepository repository;

    @Override
    public Block save(Block block) {
        return repository.save(block);
    }

    @Override
    public Optional<Block> findByBloqueadorIdAndBloqueadoId(String bloqueadorId, String bloqueadoId) {
        return repository.findByBloqueadorIdAndBloqueadoId(bloqueadorId, bloqueadoId);
    }

    @Override
    public void delete(Block block) {
        repository.delete(block);
    }
}
