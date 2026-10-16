package com.comunidev.comunidevbackend.message.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/** Un mensaje suelto dentro de una conversacion. */
@Getter
@Setter
@Document(collection = "messages")
@CompoundIndex(name = "idx_messages_conversation",
        def = "{'conversationId': 1, 'createdAt': -1}")
public class Message extends AggregateRoot<String> {
    @Indexed
    private String conversationId;
    private String emisorId;
    private String texto;
    private Boolean leido;
    private Instant createdAt;

    public static Message create(String conversationId, String emisorId, String texto) {
        Message message = new Message();
        message.setConversationId(conversationId);
        message.setEmisorId(emisorId);
        message.setTexto(texto);
        message.setLeido(false);
        message.setCreatedAt(Instant.now());
        return message;
    }
}
