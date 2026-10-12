package com.comunidev.comunidevbackend.post.adapter.in.graphql;

import com.comunidev.comunidevbackend.follow.application.port.out.FollowRepositoryPort;
import com.comunidev.comunidevbackend.follow.domain.Follow;
import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.post.domain.Post;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostGraphQLResolver {

    /** Tope por request para que un cliente no pueda pedir la coleccion entera. */
    private static final int MAX_PAGE_SIZE = 50;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final PostRepositoryPort postRepositoryPort;
    private final FollowRepositoryPort followRepositoryPort;

    /**
     * Listado general paginado. Antes era {@code findAll()}, que devolvia la
     * coleccion completa.
     */
    @QueryMapping
    public List<Post> posts(@Argument Integer page, @Argument Integer size) {
        int pageNum = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? Math.min(size, MAX_PAGE_SIZE) : DEFAULT_PAGE_SIZE;
        return postRepositoryPort.findFeedPage(List.of(), pageNum * pageSize, pageSize);
    }

    @QueryMapping
    public Post post(@Argument String id) {
        return postRepositoryPort.findById(id).orElse(null);
    }

    /**
     * El feed pagina en la base de datos: primero los posts de quienes sigo,
     * despues el resto. Antes se cargaban TODOS los posts en memoria para
     * hacer subList(), lo que crecia linealmente con la coleccion.
     */
    @QueryMapping
    public List<Post> feed(
            @Argument String userId,
            @Argument Integer page,
            @Argument Integer size) {

        int pageNum = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? Math.min(size, MAX_PAGE_SIZE) : DEFAULT_PAGE_SIZE;

        List<String> followingIds = followRepositoryPort.findBySeguidorId(userId).stream()
                .map(Follow::getSeguidoId)
                .toList();

        return postRepositoryPort.findFeedPage(followingIds, pageNum * pageSize, pageSize);
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
        postRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepositoryPort.deleteById(id);
        return true;
    }
}
