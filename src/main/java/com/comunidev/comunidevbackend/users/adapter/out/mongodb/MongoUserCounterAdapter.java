package com.comunidev.comunidevbackend.users.adapter.out.mongodb;

import com.comunidev.comunidevbackend.users.application.port.out.UserCounterPort;
import com.comunidev.comunidevbackend.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MongoUserCounterAdapter implements UserCounterPort {

    private static final String SEGUIDORES = "seguidoresCount";
    private static final String SIGUIENDO = "siguiendoCount";
    private static final String CONEXIONES = "conexionesCount";

    private final MongoTemplate mongoTemplate;

    @Override
    public void incrementSeguidores(String userId) {
        inc(userId, SEGUIDORES, 1);
    }

    @Override
    public void decrementSeguidores(String userId) {
        inc(userId, SEGUIDORES, -1);
    }

    @Override
    public void incrementSiguiendo(String userId) {
        inc(userId, SIGUIENDO, 1);
    }

    @Override
    public void decrementSiguiendo(String userId) {
        inc(userId, SIGUIENDO, -1);
    }

    @Override
    public void incrementConexiones(String userId) {
        inc(userId, CONEXIONES, 1);
    }

    @Override
    public void decrementConexiones(String userId) {
        inc(userId, CONEXIONES, -1);
    }

    private void inc(String userId, String field, int delta) {
        if (userId == null || userId.isBlank()) {
            return;
        }
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().inc(field, delta)
                .set("updatedAt", java.time.Instant.now());
        mongoTemplate.updateFirst(query, update, User.class);

        Query clamp = new Query(Criteria.where("_id").is(userId).and(field).lt(0));
        mongoTemplate.updateFirst(clamp, new Update().set(field, 0), User.class);
    }
}
