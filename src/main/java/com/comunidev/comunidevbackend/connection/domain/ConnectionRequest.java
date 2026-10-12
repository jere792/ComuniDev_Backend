package com.comunidev.comunidevbackend.connection.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "connection_requests")
// Bandeja de solicitudes pendientes del receptor, y las que envié.
@CompoundIndex(name = "idx_connreq_receptor_estado",
        def = "{'receptorId': 1, 'estado': 1, 'createdAt': -1}")
@CompoundIndex(name = "idx_connreq_pair_estado",
        def = "{'solicitanteId': 1, 'receptorId': 1, 'estado': 1}")
public class ConnectionRequest extends AggregateRoot<String> {
    @Indexed
    private String solicitanteId;
    @Indexed
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
