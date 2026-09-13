package com.comunidev.comunidevbackend.reel.application.port.out;

import com.comunidev.comunidevbackend.reel.domain.Reel;

import java.util.List;
import java.util.Optional;

public interface ReelRepositoryPort {
    Reel save(Reel reel);
    Optional<Reel> findById(String id);
    List<Reel> findAll();
    List<Reel> findByAutorId(String autorId);
    void deleteById(String id);
}
