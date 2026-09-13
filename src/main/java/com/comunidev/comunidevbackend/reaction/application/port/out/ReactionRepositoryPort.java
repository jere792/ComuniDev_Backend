package com.comunidev.comunidevbackend.reaction.application.port.out;

import com.comunidev.comunidevbackend.reaction.domain.Reaction;

import java.util.List;
import java.util.Optional;

public interface ReactionRepositoryPort {
    Reaction save(Reaction reaction);
    List<Reaction> findByObjetivoId(String objetivoId);
    Optional<Reaction> findByUsuarioIdAndObjetivoId(String usuarioId, String objetivoId);
    long countByObjetivoId(String objetivoId);
    void delete(Reaction reaction);
}
