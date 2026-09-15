package com.comunidev.comunidevbackend.connection.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "connection_requests")
public class ConnectionRequest extends AggregateRoot<String> {
    private String solicitanteId;
    private String receptorId;
    private String mensaje;
    private String estado;
    private Instant createdAt;
    private Instant respondidoEn;
    private Instant updatedAt;

    public static ConnectionRequest create(String solicitanteId, String receptorId, String mensaje) {
        ConnectionRequest request = new ConnectionRequest();
        request.setSolicitanteId(solicitanteId);
        request.setReceptorId(receptorId);
        request.setMensaje(mensaje);
        request.setEstado("PENDIENTE");
        request.setCreatedAt(Instant.now());
        request.setUpdatedAt(Instant.now());
        return request;
    }
}
