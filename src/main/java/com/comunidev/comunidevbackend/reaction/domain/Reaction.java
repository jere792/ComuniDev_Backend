package com.comunidev.comunidevbackend.reaction.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "reactions")
public class Reaction extends AggregateRoot<String> {
    private String usuarioId;
    private String objetivoId;
    private String tipoObjetivo;
    private TipoReaccion tipoReaccion;
    private Instant createdAt;

    public static Reaction create(String usuarioId, String objetivoId, String tipoObjetivo, TipoReaccion tipoReaccion) {
        Reaction reaction = new Reaction();
        reaction.setUsuarioId(usuarioId);
        reaction.setObjetivoId(objetivoId);
        reaction.setTipoObjetivo(tipoObjetivo);
        reaction.setTipoReaccion(tipoReaccion);
        reaction.setCreatedAt(Instant.now());
        return reaction;
    }
}
