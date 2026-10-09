package com.comunidev.comunidevbackend.comment.application.port.out;

import com.comunidev.comunidevbackend.comment.domain.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentRepositoryPort {
    Comment save(Comment comment);
    Optional<Comment> findById(String id);
    List<Comment> findByContenidoId(String contenidoId);
    List<Comment> findByParentCommentId(String parentCommentId);
    long countByContenidoId(String contenidoId);
    long countByContenidoIdAndTipoContenido(String contenidoId, String tipoContenido);
    Map<String, Long> countRepliesByParentIds(Collection<String> parentIds);
    void deleteById(String id);
}
