package com.comunidev.comunidevbackend.notification.adapter.out.mongodb;

import com.comunidev.comunidevbackend.notification.application.port.out.NotificationRepositoryPort;
import com.comunidev.comunidevbackend.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoNotificationAdapter implements NotificationRepositoryPort {

    private final MongoNotificationRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public List<Notification> findByDestinatarioId(String destinatarioId, Boolean leida) {
        if (leida == null) {
            return repository.findByDestinatarioIdOrderByCreatedAtDesc(destinatarioId);
        }
        return repository.findByDestinatarioIdAndLeida(destinatarioId, leida);
    }

    @Override
    public int countUnreadByDestinatarioId(String destinatarioId) {
        return (int) repository.countByDestinatarioIdAndLeida(destinatarioId, false);
    }

    @Override
    public Optional<Notification> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void markAsRead(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("leida", true);
        mongoTemplate.updateFirst(query, update, Notification.class);
    }

    @Override
    public void markAllAsRead(String destinatarioId) {
        Query query = new Query(Criteria.where("destinatarioId").is(destinatarioId).and("leida").is(false));
        Update update = new Update().set("leida", true);
        mongoTemplate.updateMulti(query, update, Notification.class);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
