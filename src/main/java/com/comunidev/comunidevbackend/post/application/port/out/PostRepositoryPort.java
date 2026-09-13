package com.comunidev.comunidevbackend.post.application.port.out;

import com.comunidev.comunidevbackend.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {
    Post save(Post post);
    Optional<Post> findById(String id);
    List<Post> findAll();
    List<Post> findByAutorIdIn(List<String> autorIds);
    List<Post> findByAutorIdNotIn(List<String> autorIds);
    List<Post> findByAutorId(String autorId);
    void deleteById(String id);
}
