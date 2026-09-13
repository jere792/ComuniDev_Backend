package com.comunidev.comunidevbackend.follow.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "follows")
public class Follow extends AggregateRoot<String> {
    private String seguidorId;
    private String tipoSeguido;
    private String seguidoId;
    private Instant createdAt;

    public static Follow create(String seguidorId, String tipoSeguido, String seguidoId) {
        Follow follow = new Follow();
        follow.setSeguidorId(seguidorId);
        follow.setTipoSeguido(tipoSeguido);
        follow.setSeguidoId(seguidoId);
        follow.setCreatedAt(Instant.now());
        return follow;
    }
}
