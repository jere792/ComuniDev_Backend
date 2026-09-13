package com.comunidev.comunidevbackend.comment.adapter.out.mongodb;

import com.comunidev.comunidevbackend.comment.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoCommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByContenidoIdOrderByCreatedAtAsc(String contenidoId);
    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(String parentCommentId);
    long countByContenidoId(String contenidoId);
}
