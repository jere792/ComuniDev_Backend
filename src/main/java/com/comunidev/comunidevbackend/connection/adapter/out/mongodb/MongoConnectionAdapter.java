package com.comunidev.comunidevbackend.connection.adapter.out.mongodb;

import com.comunidev.comunidevbackend.connection.application.port.out.ConnectionRepositoryPort;
import com.comunidev.comunidevbackend.connection.domain.Connection;
import com.comunidev.comunidevbackend.connection.domain.ConnectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoConnectionAdapter implements ConnectionRepositoryPort {

    private final MongoConnectionRequestRepository requestRepository;
    private final MongoConnectionRepository connectionRepository;

    @Override
    public ConnectionRequest saveRequest(ConnectionRequest request) {
        return requestRepository.save(request);
    }

    @Override
    public List<ConnectionRequest> findPendingRequestsByReceptorId(String receptorId) {
        return requestRepository.findByReceptorIdAndEstado(receptorId, "PENDIENTE");
    }

    @Override
    public List<ConnectionRequest> findRequestsBySolicitanteId(String solicitanteId) {
        return requestRepository.findBySolicitanteId(solicitanteId);
    }

    @Override
    public Optional<ConnectionRequest> findPendingBySolicitanteIdAndReceptorId(String solicitanteId, String receptorId) {
        return requestRepository.findBySolicitanteIdAndReceptorIdAndEstado(solicitanteId, receptorId, "PENDIENTE");
    }

    @Override
    public Optional<ConnectionRequest> findPendingBetweenUsers(String userId, String otherUserId) {
        return requestRepository.findFirstBySolicitanteIdAndReceptorIdAndEstadoOrSolicitanteIdAndReceptorIdAndEstado(
                userId, otherUserId, "PENDIENTE",
                otherUserId, userId, "PENDIENTE");
    }

    @Override
    public Optional<ConnectionRequest> findRequestById(String id) {
        return requestRepository.findById(id);
    }

    @Override
    public void deleteRequest(ConnectionRequest request) {
        requestRepository.delete(request);
    }

    @Override
    public Connection saveConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    @Override
    public Optional<Connection> findByUsers(String usuarioMenorId, String usuarioMayorId) {
        return connectionRepository.findByUsuarioMenorIdAndUsuarioMayorId(usuarioMenorId, usuarioMayorId);
    }

    @Override
    public Optional<Connection> findByEitherUser(String userId, String otherUserId) {
        String id1 = userId.compareTo(otherUserId) < 0 ? userId : otherUserId;
        String id2 = userId.compareTo(otherUserId) < 0 ? otherUserId : userId;
        return connectionRepository.findByUsuarioMenorIdAndUsuarioMayorId(id1, id2);
    }

    @Override
    public List<Connection> findByUserId(String userId) {
        return connectionRepository.findByUsuarioMenorIdOrUsuarioMayorId(userId, userId);
    }

    @Override
    public void deleteConnection(Connection connection) {
        connectionRepository.delete(connection);
    }
}
