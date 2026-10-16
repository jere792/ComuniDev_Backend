package com.comunidev.comunidevbackend.message.application.service;

import com.comunidev.comunidevbackend.message.application.port.out.MessageRepositoryPort;
import com.comunidev.comunidevbackend.message.domain.Conversation;
import com.comunidev.comunidevbackend.message.domain.Message;
import com.comunidev.comunidevbackend.shared.application.RealtimeEventPublisher;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    /** Cortesia para no llenar la base con murallas de texto. */
    private static final int LARGO_MAXIMO = 2000;

    private final MessageRepositoryPort messageRepositoryPort;
    private final RealtimeEventPublisher realtimeEventPublisher;

    /**
     * Guarda el mensaje, actualiza el hilo y avisa a las dos personas por el canal
     * de tiempo real. Devuelve null si no hay texto, para que el formulario no
     * lance una excepcion por algo que el usuario puede corregir.
     */
    public Message enviarMensaje(String emisorId, String receptorId, String texto) {
        String emisor = exigirParticipante(emisorId, "Falta quien envia el mensaje");
        String receptor = exigirParticipante(receptorId, "Falta quien recibe el mensaje");

        if (emisor.equals(receptor)) {
            throw new BusinessException("No puedes enviarte un mensaje a ti mismo");
        }

        String cuerpo = texto == null ? "" : texto.trim();
        if (cuerpo.isEmpty()) {
            throw new BusinessException("El mensaje no puede estar vacio");
        }
        if (cuerpo.length() > LARGO_MAXIMO) {
            throw new BusinessException("El mensaje supera los " + LARGO_MAXIMO + " caracteres");
        }

        Conversation conversation = obtenerOCrear(emisor, receptor);
        conversation.registrarMensaje(emisor, cuerpo);
        // Quien responde ya vio el hilo: su propio no leido no deberia quedar.
        conversation.marcarLeidoPor(emisor);
        messageRepositoryPort.saveConversation(conversation);

        Message saved = messageRepositoryPort.saveMessage(Message.create(conversation.getId(), emisor, cuerpo));

        // Se publica a los dos: el emisor ve su mensaje en otras pestanias y el
        // receptor lo recibe al instante. El cliente quita el duplicado por id.
        realtimeEventPublisher.publishMessage(emisor, saved);
        realtimeEventPublisher.publishMessage(receptor, saved);

        return saved;
    }

    /** Busca el hilo del par o lo crea; nunca hay dos por la misma pareja. */
    public Conversation obtenerOCrear(String userIdA, String userIdB) {
        return messageRepositoryPort.findConversationByClave(Conversation.claveDe(userIdA, userIdB))
                .orElseGet(() -> messageRepositoryPort.saveConversation(Conversation.create(userIdA, userIdB)));
    }

    public List<Message> obtenerMensajes(String userId, String conversationId) {
        Conversation conversation = buscarComoParticipante(userId, conversationId);
        if (conversation == null) return List.of();
        return messageRepositoryPort.findMessagesByConversationId(conversation.getId());
    }

    public List<Conversation> obtenerConversaciones(String userId) {
        return messageRepositoryPort.findConversationsByUserId(userId);
    }

    public boolean marcarLeido(String userId, String conversationId) {
        Conversation conversation = buscarComoParticipante(userId, conversationId);
        if (conversation == null) return false;
        conversation.marcarLeidoPor(userId);
        messageRepositoryPort.saveConversation(conversation);
        return true;
    }

    /** Solo quien participa del hilo puede leerlo o marcarlo. */
    private Conversation buscarComoParticipante(String userId, String conversationId) {
        if (conversationId == null || conversationId.isBlank()) return null;
        return messageRepositoryPort.findConversationById(conversationId)
                .filter(c -> c.getParticipantes() != null && c.getParticipantes().contains(userId))
                .orElse(null);
    }

    private String exigirParticipante(String userId, String mensaje) {
        if (userId == null || userId.isBlank()) throw new BusinessException(mensaje);
        return userId;
    }
}
