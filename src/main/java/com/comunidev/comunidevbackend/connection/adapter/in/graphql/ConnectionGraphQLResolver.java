package com.comunidev.comunidevbackend.connection.adapter.in.graphql;

import com.comunidev.comunidevbackend.connection.application.port.out.ConnectionRepositoryPort;
import com.comunidev.comunidevbackend.connection.domain.Connection;
import com.comunidev.comunidevbackend.connection.domain.ConnectionRequest;
import com.comunidev.comunidevbackend.notification.application.port.out.NotificationRepositoryPort;
import com.comunidev.comunidevbackend.notification.domain.Notification;
import com.comunidev.comunidevbackend.shared.application.RealtimeEventPublisher;
import com.comunidev.comunidevbackend.users.application.service.UserCounterService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ConnectionGraphQLResolver {

    private final ConnectionRepositoryPort connectionRepositoryPort;
    private final NotificationRepositoryPort notificationRepositoryPort;
    private final UserCounterService userCounterService;
    private final RealtimeEventPublisher realtimeEventPublisher;

    @Data
    public static class ConnectionStatus {
        private String status;
        private String userId;
        private String otherUserId;
        private String requestId;
        private String connectionId;
    }

    @QueryMapping
    public ConnectionStatus connectionStatus(@Argument String userId, @Argument String otherUserId) {
        ConnectionStatus result = new ConnectionStatus();
        result.setUserId(userId);
        result.setOtherUserId(otherUserId);

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

        realtimeEventPublisher.publishNotification(receptorId, notification);
        realtimeEventPublisher.publishConnectionStatus(receptorId, status(receptorId, solicitanteId, "PENDING_RECEIVED", saved.getId(), null));
        realtimeEventPublisher.publishConnectionStatus(solicitanteId, status(solicitanteId, receptorId, "PENDING_SENT", saved.getId(), null));

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

        var existingConnection = connectionRepositoryPort.findByUsers(menor, mayor);
        if (existingConnection.isPresent() && "ACTIVA".equals(existingConnection.get().getEstado())) {
            return existingConnection.get();
        }

        Connection connection = Connection.create(menor, mayor, request.getSolicitanteId());
        Connection saved = connectionRepositoryPort.saveConnection(connection);
        userCounterService.onConnectionAccepted(id1, id2);

        Notification notification = Notification.create(
                request.getSolicitanteId(),
                request.getReceptorId(),
                "CONEXION_ACEPTADA",
                "Solicitud aceptada",
                "Tu solicitud de conexion ha sido aceptada",
                null
        );
        notificationRepositoryPort.save(notification);

        realtimeEventPublisher.publishNotification(request.getSolicitanteId(), notification);
        realtimeEventPublisher.publishConnectionStatus(id1, status(id1, id2, "CONNECTED", null, saved.getId()));
        realtimeEventPublisher.publishConnectionStatus(id2, status(id2, id1, "CONNECTED", null, saved.getId()));

        return saved;
    }

    @MutationMapping
    public Boolean rejectConnection(@Argument String requestId, @Argument String actorId) {
        var requestOpt = connectionRepositoryPort.findRequestById(requestId);
        if (requestOpt.isEmpty()) return false;

        ConnectionRequest request = requestOpt.get();
        request.setEstado("RECHAZADA");
        request.setRespondidoEn(java.time.Instant.now());
        request.setUpdatedAt(java.time.Instant.now());
        connectionRepositoryPort.saveRequest(request);

        boolean cancelledBySolicitante = actorId != null && actorId.equals(request.getSolicitanteId());
        if (!cancelledBySolicitante) {
            Notification rejectNotification = Notification.create(
                    request.getSolicitanteId(),
                    request.getReceptorId(),
                    "SOLICITUD_RECHAZADA",
                    "Solicitud de conexion rechazada",
                    "Tu solicitud de conexion ha sido rechazada",
                    null
            );
            notificationRepositoryPort.save(rejectNotification);
            realtimeEventPublisher.publishNotification(request.getSolicitanteId(), rejectNotification);
        }

        realtimeEventPublisher.publishConnectionStatus(request.getSolicitanteId(), status(request.getSolicitanteId(), request.getReceptorId(), "NONE", null, null));
        realtimeEventPublisher.publishConnectionStatus(request.getReceptorId(), status(request.getReceptorId(), request.getSolicitanteId(), "NONE", null, null));
        return true;
    }

    @MutationMapping
    public Boolean removeConnection(@Argument String connectionId) {
        var connectionOpt = connectionRepositoryPort.findById(connectionId);
        return connectionOpt
                .filter(c -> "ACTIVA".equals(c.getEstado()))
                .map(connection -> {
                    connection.setEstado("ELIMINADA");
                    connection.setUpdatedAt(java.time.Instant.now());
                    connectionRepositoryPort.saveConnection(connection);
                    userCounterService.onConnectionRemoved(
                            connection.getUsuarioMenorId(),
                            connection.getUsuarioMayorId());
                    realtimeEventPublisher.publishConnectionStatus(
                            connection.getUsuarioMenorId(),
                            status(connection.getUsuarioMenorId(), connection.getUsuarioMayorId(), "NONE", null, null));
                    realtimeEventPublisher.publishConnectionStatus(
                            connection.getUsuarioMayorId(),
                            status(connection.getUsuarioMayorId(), connection.getUsuarioMenorId(), "NONE", null, null));
                    return true;
                })
                .orElse(false);
    }

    @SubscriptionMapping
    public Flux<ConnectionStatus> connectionStatusChanged(@Argument String userId) {
        return realtimeEventPublisher.connectionStatusStream(userId)
                .filter(ConnectionStatus.class::isInstance)
                .map(ConnectionStatus.class::cast);
    }

    private ConnectionStatus status(String userId, String otherUserId, String status, String requestId, String connectionId) {
        ConnectionStatus s = new ConnectionStatus();
        s.setUserId(userId);
        s.setOtherUserId(otherUserId);
        s.setStatus(status);
        s.setRequestId(requestId);
        s.setConnectionId(connectionId);
        return s;
    }
}
