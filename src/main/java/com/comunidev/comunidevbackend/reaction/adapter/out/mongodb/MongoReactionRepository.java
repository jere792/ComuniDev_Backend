package com.comunidev.comunidevbackend.reaction.adapter.out.mongodb;

import com.comunidev.comunidevbackend.reaction.domain.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoReactionRepository extends MongoRepository<Reaction, String> {
    List<Reaction> findByObjetivoId(String objetivoId);
    Optional<Reaction> findByUsuarioIdAndObjetivoId(String usuarioId, String objetivoId);
    long countByObjetivoId(String objetivoId);
}
