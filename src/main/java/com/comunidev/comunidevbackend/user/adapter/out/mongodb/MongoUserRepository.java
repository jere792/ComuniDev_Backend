package com.comunidev.comunidevbackend.user.adapter.out.mongodb;

import com.comunidev.comunidevbackend.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    boolean existsByNombreUsuario(String nombreUsuario);
}
