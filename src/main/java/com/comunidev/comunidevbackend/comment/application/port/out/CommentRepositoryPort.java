package com.comunidev.comunidevbackend.comment.application.port.out;

import com.comunidev.comunidevbackend.comment.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryPort {
    Comment save(Comment comment);
    Optional<Comment> findById(String id);
    List<Comment> findByContenidoId(String contenidoId);
    List<Comment> findByParentCommentId(String parentCommentId);
    long countByContenidoId(String contenidoId);
    void deleteById(String id);
}
