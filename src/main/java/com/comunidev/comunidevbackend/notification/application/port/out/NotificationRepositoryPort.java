package com.comunidev.comunidevbackend.notification.application.port.out;

import com.comunidev.comunidevbackend.notification.domain.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);
    List<Notification> findByDestinatarioId(String destinatarioId, Boolean leida);
    int countUnreadByDestinatarioId(String destinatarioId);
    Optional<Notification> findById(String id);
    void markAsRead(String id);
    void markAllAsRead(String destinatarioId);
    void delete(String id);
}
