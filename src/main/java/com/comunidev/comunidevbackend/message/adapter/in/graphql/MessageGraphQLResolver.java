package com.comunidev.comunidevbackend.message.adapter.in.graphql;

import com.comunidev.comunidevbackend.message.application.service.MessageService;
import com.comunidev.comunidevbackend.message.domain.Conversation;
import com.comunidev.comunidevbackend.message.domain.Message;
import com.comunidev.comunidevbackend.shared.application.RealtimeEventPublisher;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageGraphQLResolver {

    private final MessageService messageService;
    private final RealtimeEventPublisher realtimeEventPublisher;

    /**
     * La conversacion guardada trae los no leidos como un mapa por persona; aca
     * se proyecta el que le toca a quien consulta, que es lo que la lista puede
     * pintar.
     */
    @Data
    public static class ConversationView {
        private String id;
        private List<String> participantes;
        private String otroUserId;
        private String ultimoMensajeTexto;
        private String ultimoMensajeEmisorId;
        private String ultimoMensajeEn;
        private Integer mensajesNoLeidos;
        private String createdAt;
        private String updatedAt;
        /** True si para quien consulta el hilo sigue esperando aceptacion. */
        private Boolean esSolicitud;
    }

    @QueryMapping
    public List<ConversationView> conversations(@Argument String userId) {
        return messageService.obtenerConversaciones(userId).stream()
                .map(c -> aVista(c, userId))
                .toList();
    }

    @QueryMapping
    public List<ConversationView> messageRequests(@Argument String userId) {
        return messageService.obtenerSolicitudes(userId).stream()
                .map(c -> aVista(c, userId))
                .toList();
    }

    @QueryMapping
    public List<Message> messages(@Argument String userId, @Argument String conversationId) {
        return messageService.obtenerMensajes(userId, conversationId);
    }

    @MutationMapping
    public Message sendMessage(@Argument String emisorId, @Argument String receptorId, @Argument String texto) {
        return messageService.enviarMensaje(emisorId, receptorId, texto);
    }

    @MutationMapping
    public Boolean markConversationRead(@Argument String userId, @Argument String conversationId) {
        return messageService.marcarLeido(userId, conversationId);
    }

    @MutationMapping
    public Boolean acceptConversation(@Argument String userId, @Argument String conversationId) {
        return messageService.aceptar(userId, conversationId);
    }

    @MutationMapping
    public Boolean rejectConversation(@Argument String userId, @Argument String conversationId) {
        return messageService.rechazar(userId, conversationId);
    }

    @SubscriptionMapping
    public Flux<Message> messageReceived(@Argument String userId) {
        return realtimeEventPublisher.messageStream(userId)
                .filter(Message.class::isInstance)
                .map(Message.class::cast);
    }

    private ConversationView aVista(Conversation conversation, String userId) {
        ConversationView view = new ConversationView();
        view.setId(conversation.getId());
        view.setParticipantes(conversation.getParticipantes());
        view.setOtroUserId(conversation.otroParticipante(userId));
        view.setUltimoMensajeTexto(conversation.getUltimoMensajeTexto());
        view.setUltimoMensajeEmisorId(conversation.getUltimoMensajeEmisorId());
        view.setUltimoMensajeEn(aTexto(conversation.getUltimoMensajeEn()));
        view.setMensajesNoLeidos((int) conversation.noLeidosPara(userId));
        view.setCreatedAt(aTexto(conversation.getCreatedAt()));
        view.setUpdatedAt(aTexto(conversation.getUpdatedAt()));
        view.setEsSolicitud(conversation.esSolicitudPara(userId));
        return view;
    }

    private String aTexto(Instant momento) {
        return momento == null ? null : momento.toString();
    }
}
