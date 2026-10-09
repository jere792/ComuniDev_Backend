package com.comunidev.comunidevbackend.story.adapter.out.mongodb;

import com.comunidev.comunidevbackend.story.application.port.out.StoryRepositoryPort;
import com.comunidev.comunidevbackend.story.domain.Story;
import com.comunidev.comunidevbackend.story.domain.StoryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoStoryAdapter implements StoryRepositoryPort {

    private final MongoStoryRepository storyRepository;
    private final MongoStoryViewRepository viewRepository;

    @Override
    public Story save(Story story) {
        return storyRepository.save(story);
    }

    @Override
    public Optional<Story> findById(String id) {
        return storyRepository.findById(id);
    }

    @Override
    public List<Story> findRecentByAutorIds(List<String> autorIds, Instant since) {
        return storyRepository.findByAutorIdInAndCreatedAtAfterOrderByCreatedAtDesc(autorIds, since);
    }

    @Override
    public List<Story> findAllByAutorId(String autorId) {
        return storyRepository.findByAutorIdOrderByCreatedAtDesc(autorId);
    }

    @Override
    public List<Story> findAllByAutorIdAndCategoriaId(String autorId, String categoriaId) {
        return storyRepository.findByAutorIdAndCategoriaIdsContainingOrderByCreatedAtDesc(autorId, categoriaId);
    }

    @Override
    public List<Story> findByCategoriaId(String categoriaId) {
        return storyRepository.findByCategoriaIdsContaining(categoriaId);
    }

    @Override
    public StoryView saveView(StoryView view) {
        return viewRepository.save(view);
    }

    @Override
    public List<StoryView> findViewsByStoryId(String storyId) {
        return viewRepository.findByStoryIdOrderByViewedAtAsc(storyId);
    }

    @Override
    public Optional<StoryView> findViewByStoryIdAndViewerId(String storyId, String viewerId) {
        return viewRepository.findByStoryIdAndViewerId(storyId, viewerId);
    }

    @Override
    public void deleteById(String id) {
        storyRepository.deleteById(id);
    }
}
