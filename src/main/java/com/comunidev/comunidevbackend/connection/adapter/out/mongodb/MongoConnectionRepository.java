package com.comunidev.comunidevbackend.connection.adapter.out.mongodb;

import com.comunidev.comunidevbackend.connection.domain.Connection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoConnectionRepository extends MongoRepository<Connection, String> {
    Optional<Connection> findByUsuarioMenorIdAndUsuarioMayorId(String usuarioMenorId, String usuarioMayorId);
    List<Connection> findByUsuarioMenorIdOrUsuarioMayorId(String usuarioMenorId, String usuarioMayorId);
    Optional<Connection> findByUsuarioMenorIdAndUsuarioMayorIdOrUsuarioMenorIdAndUsuarioMayorId(
            String usuarioMenorId1, String usuarioMayorId1,
            String usuarioMenorId2, String usuarioMayorId2);
}
