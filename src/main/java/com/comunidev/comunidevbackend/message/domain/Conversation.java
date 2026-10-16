package com.comunidev.comunidevbackend.message.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hilo de mensajes entre dos personas. Siempre hay dos participantes; el par se
 * guarda ordenado en {@code clave} para que exista una sola conversacion por
 * pareja, sin importar quien la haya iniciado.
 */
@Getter
@Setter
@Document(collection = "conversations")
// Un indice simple en el array de participantes no podria ser unico: Mongo indexa
// cada elemento por separado y [A,B] chocaria con [A,C] por la A. La clave lo resuelve.
@CompoundIndex(name = "idx_conversations_clave", def = "{'clave': 1}", unique = true)
@CompoundIndex(name = "idx_conversations_participantes", def = "{'participantes': 1}")
public class Conversation extends AggregateRoot<String> {
    private String clave;
    private List<String> participantes;
    private String ultimoMensajeTexto;
    private String ultimoMensajeEmisorId;
    private Instant ultimoMensajeEn;
    /** Contador por destinatario, para la bolita de no leidos de cada persona. */
    private Map<String, Long> mensajesNoLeidos;
    private Instant createdAt;
    private Instant updatedAt;

    public static Conversation create(String userIdA, String userIdB) {
        Conversation conversation = new Conversation();
        conversation.setClave(claveDe(userIdA, userIdB));
        List<String> participantes = new ArrayList<>(List.of(userIdA, userIdB));
        Collections.sort(participantes);
        conversation.setParticipantes(participantes);
        conversation.setMensajesNoLeidos(new HashMap<>());
        conversation.setCreatedAt(Instant.now());
        conversation.setUpdatedAt(Instant.now());
        return conversation;
    }

    /** Identificador comun al par, ordenado alfabeticamente. */
    public static String claveDe(String userIdA, String userIdB) {
        return userIdA.compareTo(userIdB) < 0
                ? userIdA + "|" + userIdB
                : userIdB + "|" + userIdA;
    }

    public String otroParticipante(String userId) {
        if (participantes == null) return null;
        return participantes.stream().filter(p -> !p.equals(userId)).findFirst().orElse(null);
    }

    /** Apunta el ultimo mensaje al recien enviado y suma el no leido del receptor. */
    public void registrarMensaje(String emisorId, String texto) {
        this.ultimoMensajeTexto = texto;
        this.ultimoMensajeEmisorId = emisorId;
        this.ultimoMensajeEn = Instant.now();
        this.updatedAt = Instant.now();

        String receptorId = otroParticipante(emisorId);
        if (receptorId == null) return;
        if (this.mensajesNoLeidos == null) this.mensajesNoLeidos = new HashMap<>();
        long actual = this.mensajesNoLeidos.getOrDefault(receptorId, 0L);
        this.mensajesNoLeidos.put(receptorId, actual + 1);
    }

    public void marcarLeidoPor(String userId) {
        if (this.mensajesNoLeidos != null) this.mensajesNoLeidos.put(userId, 0L);
        this.updatedAt = Instant.now();
    }

    /** No leidos que quedan para la persona que mira la lista. */
    public long noLeidosPara(String userId) {
        return mensajesNoLeidos == null ? 0L : mensajesNoLeidos.getOrDefault(userId, 0L);
    }
}
