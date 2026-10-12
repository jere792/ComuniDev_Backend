package com.comunidev.comunidevbackend.block.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "blocks")
// isBlocked(bloqueador, bloqueado) es la consulta que llama el feed en cada request.
@CompoundIndex(name = "idx_blocks_pair",
        def = "{'bloqueadorId': 1, 'bloqueadoId': 1}", unique = true)
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
