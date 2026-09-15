package com.comunidev.comunidevbackend.notification.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "notifications")
public class Notification extends AggregateRoot<String> {
    private String destinatarioId;
    private String actorId;
    private String tipo;
    private String titulo;
    private String mensaje;
    private NotificationReferencia referencia;
    private boolean leida;
    private Instant createdAt;

    public static Notification create(String destinatarioId, String actorId, String tipo, String titulo, String mensaje, NotificationReferencia referencia) {
        Notification notification = new Notification();
        notification.setDestinatarioId(destinatarioId);
        notification.setActorId(actorId);
        notification.setTipo(tipo);
        notification.setTitulo(titulo);
        notification.setMensaje(mensaje);
        notification.setReferencia(referencia);
        notification.setLeida(false);
        notification.setCreatedAt(Instant.now());
        return notification;
    }

    @Getter
    @Setter
    public static class NotificationReferencia {
        private String tipo;
        private String id;
    }
}
