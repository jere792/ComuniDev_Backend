package com.comunidev.comunidevbackend.connection.adapter.out.mongodb;

import com.comunidev.comunidevbackend.connection.domain.ConnectionRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoConnectionRequestRepository extends MongoRepository<ConnectionRequest, String> {
    List<ConnectionRequest> findByReceptorIdAndEstado(String receptorId, String estado);
    List<ConnectionRequest> findBySolicitanteId(String solicitanteId);
    Optional<ConnectionRequest> findBySolicitanteIdAndReceptorIdAndEstado(String solicitanteId, String receptorId, String estado);
    Optional<ConnectionRequest> findFirstBySolicitanteIdAndReceptorIdAndEstadoOrSolicitanteIdAndReceptorIdAndEstado(
            String solicitanteId, String receptorId, String estado1,
            String solicitanteId2, String receptorId2, String estado2);
}
