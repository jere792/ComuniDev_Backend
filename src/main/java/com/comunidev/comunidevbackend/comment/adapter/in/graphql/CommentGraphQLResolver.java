package com.comunidev.comunidevbackend.comment.adapter.in.graphql;

import com.comunidev.comunidevbackend.comment.application.port.out.CommentRepositoryPort;
import com.comunidev.comunidevbackend.comment.domain.Comment;
import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.post.domain.Post;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CommentGraphQLResolver {

    private final CommentRepositoryPort commentRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;

    @QueryMapping
    public List<Comment> comments(
            @Argument String contentId,
            @Argument String contentType) {

        List<Comment> all = commentRepositoryPort.findByContenidoId(contentId);
        return all.stream()
                .filter(c -> c.getTipoContenido() != null && c.getTipoContenido().equals(contentType))
                .filter(c -> c.getParentCommentId() == null || c.getParentCommentId().isEmpty())
                .toList();
    }

    @QueryMapping
    public List<Comment> commentReplies(@Argument String parentCommentId) {
        return commentRepositoryPort.findByParentCommentId(parentCommentId);
    }

    @QueryMapping
    public int commentCount(@Argument String contentId, @Argument String contentType) {
        return (int) commentRepositoryPort.countByContenidoIdAndTipoContenido(contentId, contentType);
    }

    @QueryMapping
    public Comment comment(@Argument String id) {
        return commentRepositoryPort.findById(id).orElse(null);
    }

    @BatchMapping(typeName = "Comment", field = "repliesCount")
    public Map<Comment, Integer> repliesCount(List<Comment> comments) {
        Map<Comment, Integer> result = new HashMap<>();
        if (comments == null || comments.isEmpty()) {
            return result;
        }
        List<String> ids = comments.stream()
                .map(Comment::getId)
                .filter(Objects::nonNull)
                .toList();
        Map<String, Long> counts = commentRepositoryPort.countRepliesByParentIds(ids);
        for (Comment comment : comments) {
            long count = comment.getId() != null ? counts.getOrDefault(comment.getId(), 0L) : 0L;
            result.put(comment, (int) count);
        }
        return result;
    }

    @MutationMapping
    public Comment createComment(
            @Argument String autorId,
            @Argument String contenidoId,
            @Argument String tipoContenido,
            @Argument String parentCommentId,
            @Argument String texto,
            @Argument List<String> imagenes) {

        Comment comment = Comment.create(autorId, contenidoId, tipoContenido, parentCommentId, texto, imagenes);
        Comment saved = commentRepositoryPort.save(comment);
        if ("POST".equalsIgnoreCase(tipoContenido)) {
            incrementPostCommentCount(contenidoId, 1);
        }
        return saved;
    }

    @MutationMapping
    public Comment updateComment(
            @Argument String id,
            @Argument String texto,
            @Argument List<String> imagenes) {

        return commentRepositoryPort.findById(id)
                .map(comment -> {
                    if (texto != null) comment.setTexto(texto);
                    if (imagenes != null) comment.setImagenes(imagenes);
                    comment.setUpdatedAt(Instant.now());
                    return commentRepositoryPort.save(comment);
                })
                .orElse(null);
    }

    @MutationMapping
    public Boolean deleteComment(@Argument String id) {
        Comment comment = commentRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        if ("POST".equalsIgnoreCase(comment.getTipoContenido())) {
            incrementPostCommentCount(comment.getContenidoId(), -1);
        }
        commentRepositoryPort.deleteById(id);
        return true;
    }

    private void incrementPostCommentCount(String postId, int delta) {
        try {
            postRepositoryPort.findById(postId).ifPresent(post -> {
                if (post.getEstadisticas() == null) {
                    post.setEstadisticas(new Post.PostEstadisticas());
                }
                int current = post.getEstadisticas().getComentariosCount() != null
                        ? post.getEstadisticas().getComentariosCount() : 0;
                post.getEstadisticas().setComentariosCount(Math.max(0, current + delta));
                postRepositoryPort.save(post);
            });
        } catch (Exception ignored) {
            // counter update is best-effort, don't fail the comment operation
        }
    }
}
