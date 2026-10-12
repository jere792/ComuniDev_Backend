package com.comunidev.comunidevbackend.user.adapter.out.mongodb;

import com.comunidev.comunidevbackend.user.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoUserRepositoryAdapter implements UserRepositoryPort {

    private final MongoUserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> findByNombreUsuario(String nombreUsuario) {
        return repository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return repository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
