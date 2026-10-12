package com.comunidev.comunidevbackend.story.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "story_views")
// Quien vio cada story (lista de vista) + unicidad story+viewer para no contar dos veces.
@CompoundIndex(name = "idx_storyviews_story", def = "{'storyId': 1, 'viewedAt': 1}")
@CompoundIndex(name = "idx_storyviews_story_viewer",
        def = "{'storyId': 1, 'viewerId': 1}", unique = true)
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
