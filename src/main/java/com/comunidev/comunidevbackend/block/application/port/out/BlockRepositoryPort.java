package com.comunidev.comunidevbackend.block.application.port.out;

import com.comunidev.comunidevbackend.block.domain.Block;

import java.util.Optional;

public interface BlockRepositoryPort {
    Block save(Block block);
    Optional<Block> findByBloqueadorIdAndBloqueadoId(String bloqueadorId, String bloqueadoId);
    void delete(Block block);
}
