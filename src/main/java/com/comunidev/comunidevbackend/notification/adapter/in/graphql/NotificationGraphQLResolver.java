package com.comunidev.comunidevbackend.notification.adapter.in.graphql;

import com.comunidev.comunidevbackend.notification.application.port.out.NotificationRepositoryPort;
import com.comunidev.comunidevbackend.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationGraphQLResolver {

    private final NotificationRepositoryPort notificationRepositoryPort;

    @QueryMapping
    public List<Notification> notifications(@Argument String userId, @Argument Boolean leida) {
        return notificationRepositoryPort.findByDestinatarioId(userId, leida);
    }

    @QueryMapping
    public Integer unreadCount(@Argument String userId) {
        return notificationRepositoryPort.countUnreadByDestinatarioId(userId);
    }

    @MutationMapping
    public Boolean markNotificationAsRead(@Argument String notificationId) {
        notificationRepositoryPort.markAsRead(notificationId);
        return true;
    }

    @MutationMapping
    public Boolean markAllAsRead(@Argument String userId) {
        notificationRepositoryPort.markAllAsRead(userId);
        return true;
    }

    @MutationMapping
    public Boolean deleteNotification(@Argument String notificationId) {
        notificationRepositoryPort.delete(notificationId);
        return true;
    }
}
