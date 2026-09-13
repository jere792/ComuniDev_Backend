package com.comunidev.comunidevbackend.reaction.adapter.out.mongodb;

import com.comunidev.comunidevbackend.reaction.application.port.out.ReactionRepositoryPort;
import com.comunidev.comunidevbackend.reaction.domain.Reaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoReactionAdapter implements ReactionRepositoryPort {

    private final MongoReactionRepository repository;

    @Override
    public Reaction save(Reaction reaction) {
        return repository.save(reaction);
    }

    @Override
    public List<Reaction> findByObjetivoId(String objetivoId) {
        return repository.findByObjetivoId(objetivoId);
    }

    @Override
    public Optional<Reaction> findByUsuarioIdAndObjetivoId(String usuarioId, String objetivoId) {
        return repository.findByUsuarioIdAndObjetivoId(usuarioId, objetivoId);
    }

    @Override
    public long countByObjetivoId(String objetivoId) {
        return repository.countByObjetivoId(objetivoId);
    }

    @Override
    public void delete(Reaction reaction) {
        repository.delete(reaction);
    }
}
