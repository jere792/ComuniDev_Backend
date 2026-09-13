package com.comunidev.comunidevbackend.post.adapter.out.mongodb;

import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoPostAdapter implements PostRepositoryPort {

    private final MongoPostRepository repository;

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    @Override
    public Optional<Post> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Post> findByAutorIdIn(List<String> autorIds) {
        return repository.findByAutorIdInOrderByCreatedAtDesc(autorIds);
    }

    @Override
    public List<Post> findByAutorIdNotIn(List<String> autorIds) {
        return repository.findByAutorIdNotInOrderByCreatedAtDesc(autorIds);
    }

    @Override
    public List<Post> findByAutorId(String autorId) {
        return repository.findByAutorIdOrderByCreatedAtDesc(autorId);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
