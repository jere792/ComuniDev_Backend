package com.comunidev.comunidevbackend.story.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "story_views")
public class StoryView extends AggregateRoot<String> {
    private String storyId;
    private String viewerId;
    private Instant viewedAt;

    public static StoryView create(String storyId, String viewerId) {
        StoryView view = new StoryView();
        view.setStoryId(storyId);
        view.setViewerId(viewerId);
        view.setViewedAt(Instant.now());
        return view;
    }
}
