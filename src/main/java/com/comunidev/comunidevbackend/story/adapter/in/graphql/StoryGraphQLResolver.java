package com.comunidev.comunidevbackend.story.adapter.in.graphql;

import com.comunidev.comunidevbackend.follow.application.port.out.FollowRepositoryPort;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import com.comunidev.comunidevbackend.story.application.port.out.StoryRepositoryPort;
import com.comunidev.comunidevbackend.story.domain.Story;
import com.comunidev.comunidevbackend.story.domain.StoryView;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoryGraphQLResolver {

    private final StoryRepositoryPort storyRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;

    @QueryMapping
    public List<Story> stories(@Argument String userId) {
        List<String> followingIds = followRepositoryPort.findBySeguidorId(userId).stream()
                .map(Follow::getSeguidoId)
                .toList();

        List<String> autorIds = new java.util.ArrayList<>(followingIds);
        if (!autorIds.contains(userId)) {
            autorIds.add(userId);
        }

        if (autorIds.isEmpty()) {
            return List.of();
        }
        return storyRepositoryPort.findActiveByAutorIds(autorIds);
    }

    @QueryMapping
    public Story story(@Argument String id) {
        return storyRepositoryPort.findById(id).orElse(null);
    }

    @QueryMapping
    public List<StoryView> storyViews(@Argument String storyId) {
        return storyRepositoryPort.findViewsByStoryId(storyId);
    }

    @MutationMapping
    public Story createStory(
            @Argument String autorId,
            @Argument Story.StoryContenido contenido,
            @Argument String visibilidad) {

        Story story = Story.create(autorId, contenido, visibilidad);
        return storyRepositoryPort.save(story);
    }

    @MutationMapping
    public StoryView viewStory(@Argument String storyId, @Argument String viewerId) {

        return storyRepositoryPort.findViewByStoryIdAndViewerId(storyId, viewerId)
                .orElseGet(() -> {
                    StoryView view = storyRepositoryPort.saveView(StoryView.create(storyId, viewerId));

                    storyRepositoryPort.findById(storyId).ifPresent(story -> {
                        int current = story.getVistasCount() != null ? story.getVistasCount() : 0;
                        story.setVistasCount(current + 1);
                        storyRepositoryPort.save(story);
                    });
                    return view;
                });
    }

    @MutationMapping
    public Boolean deleteStory(@Argument String id) {
        try {
            storyRepositoryPort.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
