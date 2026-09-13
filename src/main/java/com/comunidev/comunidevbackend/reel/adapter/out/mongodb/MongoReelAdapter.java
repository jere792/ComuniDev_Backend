package com.comunidev.comunidevbackend.reel.adapter.out.mongodb;

import com.comunidev.comunidevbackend.reel.application.port.out.ReelRepositoryPort;
import com.comunidev.comunidevbackend.reel.domain.Reel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoReelAdapter implements ReelRepositoryPort {

    private final MongoReelRepository repository;

    @Override
    public Reel save(Reel reel) {
        return repository.save(reel);
    }

    @Override
    public Optional<Reel> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Reel> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Reel> findByAutorId(String autorId) {
        return repository.findByAutorIdOrderByCreatedAtDesc(autorId);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
