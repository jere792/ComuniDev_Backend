package com.comunidev.comunidevbackend.comment.adapter.out.mongodb;

import com.comunidev.comunidevbackend.comment.application.port.out.CommentRepositoryPort;
import com.comunidev.comunidevbackend.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoCommentAdapter implements CommentRepositoryPort {

    private final MongoCommentRepository repository;

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public Optional<Comment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Comment> findByContenidoId(String contenidoId) {
        return repository.findByContenidoIdOrderByCreatedAtAsc(contenidoId);
    }

    @Override
    public List<Comment> findByParentCommentId(String parentCommentId) {
        return repository.findByParentCommentIdOrderByCreatedAtAsc(parentCommentId);
    }

    @Override
    public long countByContenidoId(String contenidoId) {
        return repository.countByContenidoId(contenidoId);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
