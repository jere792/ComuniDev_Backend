package com.comunidev.comunidevbackend.message.application.port.out;

import com.comunidev.comunidevbackend.message.domain.Conversation;
import com.comunidev.comunidevbackend.message.domain.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepositoryPort {

    Conversation saveConversation(Conversation conversation);

    Optional<Conversation> findConversationById(String id);

    Optional<Conversation> findConversationByClave(String clave);

    List<Conversation> findConversationsByUserId(String userId);

    Message saveMessage(Message message);

    List<Message> findMessagesByConversationId(String conversationId);
}
