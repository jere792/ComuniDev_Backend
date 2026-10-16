package com.comunidev.comunidevbackend.message.adapter.out.mongodb;

import com.comunidev.comunidevbackend.message.domain.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findByClave(String clave);
    List<Conversation> findByParticipantesContainingOrderByUltimoMensajeEnDesc(String userId);
}
