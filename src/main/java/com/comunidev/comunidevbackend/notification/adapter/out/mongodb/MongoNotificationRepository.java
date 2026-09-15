package com.comunidev.comunidevbackend.notification.adapter.out.mongodb;

import com.comunidev.comunidevbackend.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface MongoNotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByDestinatarioIdAndLeida(String destinatarioId, boolean leida);
    List<Notification> findByDestinatarioIdOrderByCreatedAtDesc(String destinatarioId);
    long countByDestinatarioIdAndLeida(String destinatarioId, boolean leida);
}
