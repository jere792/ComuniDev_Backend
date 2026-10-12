package com.comunidev.comunidevbackend.connection.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "connections")
// El par se normaliza (menor, mayor), asi que un indice unico basta.
@CompoundIndex(name = "idx_connections_pair",
        def = "{'usuarioMenorId': 1, 'usuarioMayorId': 1}", unique = true)
public class Connection extends AggregateRoot<String> {
    private String usuarioMenorId;
    private String usuarioMayorId;
    private String iniciadorId;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;

    public static Connection create(String usuarioMenorId, String usuarioMayorId, String iniciadorId) {
        Connection connection = new Connection();
        connection.setUsuarioMenorId(usuarioMenorId);
        connection.setUsuarioMayorId(usuarioMayorId);
        connection.setIniciadorId(iniciadorId);
        connection.setEstado("ACTIVA");
        connection.setCreatedAt(Instant.now());
        connection.setUpdatedAt(Instant.now());
        return connection;
    }
}
