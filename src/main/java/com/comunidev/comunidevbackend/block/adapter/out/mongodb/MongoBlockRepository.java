package com.comunidev.comunidevbackend.block.adapter.out.mongodb;

import com.comunidev.comunidevbackend.block.domain.Block;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoBlockRepository extends MongoRepository<Block, String> {
    Optional<Block> findByBloqueadorIdAndBloqueadoId(String bloqueadorId, String bloqueadoId);
}
