package com.comunidev.comunidevbackend.story.application.port.out;

import com.comunidev.comunidevbackend.story.domain.Story;
import com.comunidev.comunidevbackend.story.domain.StoryView;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface StoryRepositoryPort {
    Story save(Story story);
    Optional<Story> findById(String id);
    List<Story> findRecentByAutorIds(List<String> autorIds, Instant since);
    List<Story> findAllByAutorId(String autorId);
    List<Story> findAllByAutorIdAndCategoriaId(String autorId, String categoriaId);
    List<Story> findByCategoriaId(String categoriaId);
    StoryView saveView(StoryView view);
    List<StoryView> findViewsByStoryId(String storyId);
    Optional<StoryView> findViewByStoryIdAndViewerId(String storyId, String viewerId);
    void deleteById(String id);
}
