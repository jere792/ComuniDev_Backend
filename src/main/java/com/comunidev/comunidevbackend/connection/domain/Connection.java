package com.comunidev.comunidevbackend.connection.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "connections")
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
