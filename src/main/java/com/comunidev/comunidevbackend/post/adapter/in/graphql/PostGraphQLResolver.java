package com.comunidev.comunidevbackend.post.adapter.in.graphql;

import com.comunidev.comunidevbackend.follow.application.port.out.FollowRepositoryPort;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostGraphQLResolver {

    private final PostRepositoryPort postRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;

    @QueryMapping
    public List<Post> posts() {
        return postRepositoryPort.findAll();
    }

    @QueryMapping
    public Post post(@Argument String id) {
        return postRepositoryPort.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Post> feed(
            @Argument String userId,
            @Argument Integer page,
            @Argument Integer size) {

        int pageNum = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        List<String> followingIds = followRepositoryPort.findBySeguidorId(userId).stream()
                .map(Follow::getSeguidoId)
                .toList();

        List<Post> feedPosts = new ArrayList<>();

        if (followingIds.isEmpty()) {
            feedPosts.addAll(postRepositoryPort.findAll());
        } else {
            feedPosts.addAll(postRepositoryPort.findByAutorIdIn(followingIds));
            feedPosts.addAll(postRepositoryPort.findByAutorIdNotIn(followingIds));
        }

        int fromIndex = Math.min(pageNum * pageSize, feedPosts.size());
        int toIndex = Math.min(fromIndex + pageSize, feedPosts.size());
        if (fromIndex >= feedPosts.size()) {
            return new ArrayList<>();
        }
        return feedPosts.subList(fromIndex, toIndex);
    }

    @MutationMapping
    public Post createPost(
            @Argument String autorId,
            @Argument Post.PostContenido contenido,
            @Argument List<String> etiquetas,
            @Argument String categoria,
            @Argument String visibilidad) {

        Post post = Post.create(autorId, contenido, etiquetas, categoria, visibilidad);
        return postRepositoryPort.save(post);
    }

    @MutationMapping
    public Post updatePost(
            @Argument String id,
            @Argument Post.PostContenido contenido,
            @Argument List<String> etiquetas,
            @Argument String categoria,
            @Argument String visibilidad) {

        return postRepositoryPort.findById(id)
                .map(post -> {
                    if (contenido != null) post.setContenido(contenido);
                    if (etiquetas != null) post.setEtiquetas(etiquetas);
                    if (categoria != null) post.setCategoria(categoria);
                    if (visibilidad != null) post.setVisibilidad(visibilidad);
                    post.setUpdatedAt(Instant.now());
                    return postRepositoryPort.save(post);
                })
                .orElse(null);
    }

    @MutationMapping
    public Boolean deletePost(@Argument String id) {
        try {
            postRepositoryPort.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
