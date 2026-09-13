package com.comunidev.comunidevbackend.comment.adapter.in.graphql;

import com.comunidev.comunidevbackend.comment.application.port.out.CommentRepositoryPort;
import com.comunidev.comunidevbackend.comment.domain.Comment;
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
        return commentRepositoryPort.save(comment);
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
            commentRepositoryPort.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
