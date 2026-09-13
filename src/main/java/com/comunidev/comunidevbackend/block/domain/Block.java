package com.comunidev.comunidevbackend.block.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "blocks")
public class Block extends AggregateRoot<String> {
    private String bloqueadorId;
    private String bloqueadoId;
    private Instant createdAt;

    public static Block create(String bloqueadorId, String bloqueadoId) {
        Block block = new Block();
        block.setBloqueadorId(bloqueadorId);
        block.setBloqueadoId(bloqueadoId);
        block.setCreatedAt(Instant.now());
        return block;
    }
}
