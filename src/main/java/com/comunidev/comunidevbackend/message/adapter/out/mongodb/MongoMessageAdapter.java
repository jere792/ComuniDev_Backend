package com.comunidev.comunidevbackend.message.adapter.out.mongodb;

import com.comunidev.comunidevbackend.message.application.port.out.MessageRepositoryPort;
import com.comunidev.comunidevbackend.message.domain.Conversation;
import com.comunidev.comunidevbackend.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoMessageAdapter implements MessageRepositoryPort {

    private final MongoConversationRepository conversationRepository;
    private final MongoMessageRepository messageRepository;

    @Override
    public Conversation saveConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public Optional<Conversation> findConversationById(String id) {
        return conversationRepository.findById(id);
    }

    @Override
    public Optional<Conversation> findConversationByClave(String clave) {
        return conversationRepository.findByClave(clave);
    }

    @Override
    public List<Conversation> findConversationsByUserId(String userId) {
        return conversationRepository.findByParticipantesContainingOrderByUltimoMensajeEnDesc(userId);
    }

    /** En Mongo seria un caos de campos nulos; se filtra en memoria con la lista ya traida. */
    @Override
    public List<Conversation> findConversacionesVisibles(String userId) {
        return findConversationsByUserId(userId).stream()
                .filter(c -> !c.esSolicitudPara(userId) && !c.esArchivadaPara(userId))
                .toList();
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findMessagesByConversationId(String conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
    }
}
