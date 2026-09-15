package com.comunidev.comunidevbackend.connection.application.port.out;

import com.comunidev.comunidevbackend.connection.domain.Connection;
import com.comunidev.comunidevbackend.connection.domain.ConnectionRequest;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepositoryPort {
    ConnectionRequest saveRequest(ConnectionRequest request);
    List<ConnectionRequest> findPendingRequestsByReceptorId(String receptorId);
    List<ConnectionRequest> findRequestsBySolicitanteId(String solicitanteId);
    Optional<ConnectionRequest> findPendingBySolicitanteIdAndReceptorId(String solicitanteId, String receptorId);
    Optional<ConnectionRequest> findPendingBetweenUsers(String userId, String otherUserId);
    Optional<ConnectionRequest> findRequestById(String id);
    void deleteRequest(ConnectionRequest request);

    Connection saveConnection(Connection connection);
    Optional<Connection> findByUsers(String usuarioMenorId, String usuarioMayorId);
    Optional<Connection> findByEitherUser(String userId, String otherUserId);
    List<Connection> findByUserId(String userId);
    void deleteConnection(Connection connection);
}
