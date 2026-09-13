package com.comunidev.comunidevbackend.story.adapter.out.mongodb;

import com.comunidev.comunidevbackend.story.domain.StoryView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoStoryViewRepository extends MongoRepository<StoryView, String> {
    List<StoryView> findByStoryIdOrderByViewedAtAsc(String storyId);
    Optional<StoryView> findByStoryIdAndViewerId(String storyId, String viewerId);
}
