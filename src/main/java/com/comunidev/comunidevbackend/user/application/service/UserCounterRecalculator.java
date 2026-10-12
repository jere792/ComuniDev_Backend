package com.comunidev.comunidevbackend.user.application.service;

import com.comunidev.comunidevbackend.connection.domain.Connection;
import com.comunidev.comunidevbackend.connection.adapter.out.mongodb.MongoConnectionRepository;
import com.comunidev.comunidevbackend.follow.adapter.out.mongodb.MongoFollowRepository;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import com.comunidev.comunidevbackend.user.adapter.out.mongodb.MongoUserRepository;
import com.comunidev.comunidevbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCounterRecalculator implements ApplicationRunner {

    private final MongoUserRepository userRepository;
    private final MongoFollowRepository followRepository;
    private final MongoConnectionRepository connectionRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        try {
            recalculate();
        } catch (Exception e) {
            log.warn("No se pudieron recalcular contadores de usuarios: {}", e.getMessage());
        }
    }

    private void recalculate() {
        List<Follow> follows = followRepository.findAll();
        List<Connection> connections = connectionRepository.findAll();
        List<User> users = userRepository.findAll();

        Map<String, Long> seguidores = new HashMap<>();
        Map<String, Long> siguiendo = new HashMap<>();
        Map<String, Long> conexiones = new HashMap<>();

        for (Follow f : follows) {
            if (f.getSeguidoId() != null) {
                seguidores.merge(f.getSeguidoId(), 1L, Long::sum);
            }
            if (f.getSeguidorId() != null) {
                siguiendo.merge(f.getSeguidorId(), 1L, Long::sum);
            }
        }

        for (Connection c : connections) {
            if (!"ACTIVA".equals(c.getEstado())) {
                continue;
            }
            if (c.getUsuarioMenorId() != null) {
                conexiones.merge(c.getUsuarioMenorId(), 1L, Long::sum);
            }
            if (c.getUsuarioMayorId() != null) {
                conexiones.merge(c.getUsuarioMayorId(), 1L, Long::sum);
            }
        }

        int updated = 0;
        for (User user : users) {
            if (user.getId() == null) {
                continue;
            }
            Query query = new Query(Criteria.where("_id").is(user.getId()));
            Update update = new Update()
                    .set("seguidoresCount", seguidores.getOrDefault(user.getId(), 0L).intValue())
                    .set("siguiendoCount", siguiendo.getOrDefault(user.getId(), 0L).intValue())
                    .set("conexionesCount", conexiones.getOrDefault(user.getId(), 0L).intValue())
                    .set("updatedAt", Instant.now());
            mongoTemplate.updateFirst(query, update, User.class);
            updated++;
        }

        log.info("Contadores recalculados para {} usuarios ({} follows, {} conexiones activas)",
                updated, follows.size(),
                connections.stream().filter(c -> "ACTIVA".equals(c.getEstado())).count());
    }
}
