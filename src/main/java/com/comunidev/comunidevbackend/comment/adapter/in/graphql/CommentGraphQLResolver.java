package com.comunidev.comunidevbackend.comment.adapter.in.graphql;

import com.comunidev.comunidevbackend.comment.application.port.out.CommentRepositoryPort;
import com.comunidev.comunidevbackend.comment.domain.Comment;
import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;

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
    public Comment comment(@Argument String id) {
        return commentRepositoryPort.findById(id).orElse(null);
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
        if ("POST".equalsIgnoreCase(tipoContenido) && (parentCommentId == null || parentCommentId.isEmpty())) {
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
        try {
            Comment comment = commentRepositoryPort.findById(id).orElse(null);
            if (comment != null && "POST".equalsIgnoreCase(comment.getTipoContenido())
                    && (comment.getParentCommentId() == null || comment.getParentCommentId().isEmpty())) {
                incrementPostCommentCount(comment.getContenidoId(), -1);
            }
            commentRepositoryPort.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
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
