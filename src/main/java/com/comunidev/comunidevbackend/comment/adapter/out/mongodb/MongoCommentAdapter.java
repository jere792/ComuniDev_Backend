package com.comunidev.comunidevbackend.comment.adapter.out.mongodb;

import com.comunidev.comunidevbackend.comment.application.port.out.CommentRepositoryPort;
import com.comunidev.comunidevbackend.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoCommentAdapter implements CommentRepositoryPort {

    private final MongoCommentRepository repository;
    private final MongoTemplate mongoTemplate;

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
    public long countByContenidoIdAndTipoContenido(String contenidoId, String tipoContenido) {
        return repository.countByContenidoIdAndTipoContenido(contenidoId, tipoContenido);
    }

    @Override
    public Map<String, Long> countRepliesByParentIds(Collection<String> parentIds) {
        Map<String, Long> counts = new HashMap<>();
        if (parentIds == null || parentIds.isEmpty()) {
            return counts;
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("parentCommentId").in(parentIds)),
                Aggregation.group("parentCommentId").count().as("count")
        );
        AggregationResults<Document> results =
                mongoTemplate.aggregate(aggregation, "comments", Document.class);
        for (Document row : results.getMappedResults()) {
            Object id = row.get("_id");
            Object count = row.get("count");
            if (id != null && count instanceof Number number) {
                counts.put(id.toString(), number.longValue());
            }
        }
        return counts;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
