package com.comunidev.comunidevbackend.post.adapter.out.mongodb;

import com.comunidev.comunidevbackend.post.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoPostRepository extends MongoRepository<Post, String> {
    List<Post> findByAutorIdInOrderByCreatedAtDesc(List<String> autorIds);
    List<Post> findByAutorIdNotInOrderByCreatedAtDesc(List<String> autorIds);
    List<Post> findByAutorIdOrderByCreatedAtDesc(String autorId);
    List<Post> findAllByOrderByCreatedAtDesc();
}
