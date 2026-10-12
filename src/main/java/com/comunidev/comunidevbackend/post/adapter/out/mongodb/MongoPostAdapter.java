package com.comunidev.comunidevbackend.post.adapter.out.mongodb;

import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoPostAdapter implements PostRepositoryPort {

    private static final String COLECCION = "posts";

    private final MongoPostRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    @Override
    public Optional<Post> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Post> findByAutorIdIn(List<String> autorIds) {
        return repository.findByAutorIdInOrderByCreatedAtDesc(autorIds);
    }

    @Override
    public List<Post> findByAutorIdNotIn(List<String> autorIds) {
        return repository.findByAutorIdNotInOrderByCreatedAtDesc(autorIds);
    }

    @Override
    public List<Post> findByAutorId(String autorId) {
        return repository.findByAutorIdOrderByCreatedAtDesc(autorId);
    }

    /**
     * Marca cada post con un flag 1/0 segun su autor este en la lista de seguidos,
     * ordena por ese flag (siguidos primero) y luego por fecha descendente, y
     * pagina con skip/limit. Todo ocurre en MongoDB: nunca se cargan los posts
     * completos en memoria para descartarlos despues.
     */
    @Override
    public List<Post> findFeedPage(List<String> followedIds, int offset, int limit) {
        List<String> seguidos = followedIds == null ? List.of() : followedIds;

        // { $cond: { if: { $in: ["$autorId", seguidos] }, then: 1, else: 0 } }
        Document condSeguido = new Document("$cond",
                new Document("if", new Document("$in", List.of("$autorId", seguidos)))
                        .append("then", 1)
                        .append("else", 0));

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.addFields()
                        .addFieldWithValue("_esSeguido", condSeguido)
                        .build(),
                Aggregation.sort(Sort.Direction.DESC, "_esSeguido", "createdAt"),
                Aggregation.skip(Math.max(0, offset)),
                Aggregation.limit(Math.max(1, limit))
        );

        AggregationResults<Post> results = mongoTemplate.aggregate(
                aggregation, COLECCION, Post.class);
        return results.getMappedResults();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
