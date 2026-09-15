package com.comunidev.comunidevbackend.connection.adapter.in.graphql;

import com.comunidev.comunidevbackend.connection.application.port.out.ConnectionRepositoryPort;
import com.comunidev.comunidevbackend.connection.domain.Connection;
import com.comunidev.comunidevbackend.connection.domain.ConnectionRequest;
import com.comunidev.comunidevbackend.notification.application.port.out.NotificationRepositoryPort;
import com.comunidev.comunidevbackend.notification.domain.Notification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ConnectionGraphQLResolver {

    private final ConnectionRepositoryPort connectionRepositoryPort;
    private final NotificationRepositoryPort notificationRepositoryPort;

    @Data
    public static class ConnectionStatus {
        private String status;
        private String requestId;
        private String connectionId;
    }

    @QueryMapping
    public ConnectionStatus connectionStatus(@Argument String userId, @Argument String otherUserId) {
        ConnectionStatus result = new ConnectionStatus();

        var existingConnection = connectionRepositoryPort.findByEitherUser(userId, otherUserId);
        if (existingConnection.isPresent()) {
            Connection conn = existingConnection.get();
            result.setStatus("CONNECTED");
            result.setConnectionId(conn.getId());
            return result;
        }

        var pendingRequest = connectionRepositoryPort.findPendingBetweenUsers(userId, otherUserId);
        if (pendingRequest.isPresent()) {
            ConnectionRequest req = pendingRequest.get();
            if (req.getSolicitanteId().equals(userId)) {
                result.setStatus("PENDING_SENT");
            } else {
                result.setStatus("PENDING_RECEIVED");
            }
            result.setRequestId(req.getId());
            return result;
        }

        result.setStatus("NONE");
        return result;
    }

    @QueryMapping
    public List<ConnectionRequest> connectionRequests(@Argument String userId) {
        return connectionRepositoryPort.findPendingRequestsByReceptorId(userId);
    }

    @QueryMapping
    public List<Connection> connections(@Argument String userId) {
        return connectionRepositoryPort.findByUserId(userId);
    }

    @MutationMapping
    public ConnectionRequest sendConnectionRequest(
            @Argument String solicitanteId,
            @Argument String receptorId,
            @Argument String mensaje) {

        if (solicitanteId.equals(receptorId)) return null;

        var existing = connectionRepositoryPort.findPendingBySolicitanteIdAndReceptorId(solicitanteId, receptorId);
        if (existing.isPresent()) return existing.get();

        ConnectionRequest request = ConnectionRequest.create(solicitanteId, receptorId, mensaje);
        ConnectionRequest saved = connectionRepositoryPort.saveRequest(request);

        Notification notification = Notification.create(
                receptorId,
                solicitanteId,
                "SOLICITUD_CONEXION",
                "Nueva solicitud de conexion",
                mensaje != null ? mensaje : "Te ha enviado una solicitud de conexion",
                null
        );
        notificationRepositoryPort.save(notification);

        return saved;
    }

    @MutationMapping
    public Connection acceptConnection(@Argument String requestId) {
        var requestOpt = connectionRepositoryPort.findRequestById(requestId);
        if (requestOpt.isEmpty()) return null;

        ConnectionRequest request = requestOpt.get();
        request.setEstado("ACEPTADA");
        request.setRespondidoEn(java.time.Instant.now());
        request.setUpdatedAt(java.time.Instant.now());
        connectionRepositoryPort.saveRequest(request);

        String id1 = request.getSolicitanteId();
        String id2 = request.getReceptorId();
        String menor = id1.compareTo(id2) < 0 ? id1 : id2;
        String mayor = id1.compareTo(id2) < 0 ? id2 : id1;

        Connection connection = Connection.create(menor, mayor, request.getSolicitanteId());
        Connection saved = connectionRepositoryPort.saveConnection(connection);

        Notification notification = Notification.create(
                request.getSolicitanteId(),
                request.getReceptorId(),
                "CONEXION_ACEPTADA",
                "Solicitud aceptada",
                "Tu solicitud de conexion ha sido aceptada",
                null
        );
        notificationRepositoryPort.save(notification);

        return saved;
    }

    @MutationMapping
    public Boolean rejectConnection(@Argument String requestId) {
        var requestOpt = connectionRepositoryPort.findRequestById(requestId);
        if (requestOpt.isEmpty()) return false;

        ConnectionRequest request = requestOpt.get();
        request.setEstado("RECHAZADA");
        request.setRespondidoEn(java.time.Instant.now());
        request.setUpdatedAt(java.time.Instant.now());
        connectionRepositoryPort.saveRequest(request);
        return true;
    }

    @MutationMapping
    public Boolean removeConnection(@Argument String connectionId) {
        var connections = connectionRepositoryPort.findByUserId(connectionId);
        return connections.stream()
                .filter(c -> c.getId().equals(connectionId))
                .findFirst()
                .map(connection -> {
                    connection.setEstado("ELIMINADA");
                    connection.setUpdatedAt(java.time.Instant.now());
                    connectionRepositoryPort.saveConnection(connection);
                    return true;
                })
                .orElse(false);
    }
}
