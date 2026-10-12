package com.comunidev.comunidevbackend.post.application.port.out;

import com.comunidev.comunidevbackend.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {
    Post save(Post post);
    Optional<Post> findById(String id);
    List<Post> findAll();
    List<Post> findByAutorIdIn(List<String> autorIds);
    List<Post> findByAutorIdNotIn(List<String> autorIds);
    List<Post> findByAutorId(String autorId);

    /**
     * Feed paginado: primero los posts de {@code followedIds}, despues el resto,
     * ambos ordenados por fecha descendente. La paginacion ocurre en la base de
     * datos, no en memoria.
     *
     * @param followedIds ids que sigue el usuario; nunca debe ser {@code null}
     * @param offset      cuantos documentos saltar
     * @param limit       maximo de documentos a devolver
     */
    List<Post> findFeedPage(List<String> followedIds, int offset, int limit);

    void deleteById(String id);
}
