package com.comunidev.comunidevbackend.message.adapter.out.mongodb;

import com.comunidev.comunidevbackend.message.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoMessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationIdOrderByCreatedAtAsc(String conversationId);
}
