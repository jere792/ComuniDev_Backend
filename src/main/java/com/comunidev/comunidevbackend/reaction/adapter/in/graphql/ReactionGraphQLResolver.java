package com.comunidev.comunidevbackend.reaction.adapter.in.graphql;

import com.comunidev.comunidevbackend.comment.application.port.out.CommentRepositoryPort;
import com.comunidev.comunidevbackend.post.application.port.out.PostRepositoryPort;
import com.comunidev.comunidevbackend.reaction.application.port.out.ReactionRepositoryPort;
import com.comunidev.comunidevbackend.reaction.domain.Reaction;
import com.comunidev.comunidevbackend.reaction.domain.TipoReaccion;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReactionGraphQLResolver {

    private final ReactionRepositoryPort reactionRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;
    private final CommentRepositoryPort commentRepositoryPort;

    @QueryMapping
    public List<Reaction> reactions(@Argument String targetId, @Argument String targetType) {
        return reactionRepositoryPort.findByObjetivoId(targetId);
    }

    @QueryMapping
    public Reaction myReaction(
            @Argument String userId,
            @Argument String targetId,
            @Argument String targetType) {
        return reactionRepositoryPort.findByUsuarioIdAndObjetivoId(userId, targetId).orElse(null);
    }

    @MutationMapping
    public Reaction react(
            @Argument String usuarioId,
            @Argument String objetivoId,
            @Argument String tipoObjetivo,
            @Argument TipoReaccion tipoReaccion) {

        Reaction reaction = reactionRepositoryPort.findByUsuarioIdAndObjetivoId(usuarioId, objetivoId)
                .map(existing -> {
                    existing.setTipoReaccion(tipoReaccion);
                    return reactionRepositoryPort.save(existing);
                })
                .orElseGet(() -> {
                    Reaction created = reactionRepositoryPort.save(
                            Reaction.create(usuarioId, objetivoId, tipoObjetivo, tipoReaccion));
                    incrementTargetCounter(objetivoId, tipoObjetivo, 1);
                    return created;
                });
        return reaction;
    }

    @MutationMapping
    public Boolean unreact(
            @Argument String usuarioId,
            @Argument String objetivoId,
            @Argument String tipoObjetivo) {

        return reactionRepositoryPort.findByUsuarioIdAndObjetivoId(usuarioId, objetivoId)
                .map(reaction -> {
                    reactionRepositoryPort.delete(reaction);
                    incrementTargetCounter(objetivoId, tipoObjetivo, -1);
                    return true;
                })
                .orElse(false);
    }

    @MutationMapping
    public Reaction changeReaction(
            @Argument String usuarioId,
            @Argument String objetivoId,
            @Argument String tipoObjetivo,
            @Argument TipoReaccion tipoReaccion) {

        return reactionRepositoryPort.findByUsuarioIdAndObjetivoId(usuarioId, objetivoId)
                .map(existing -> {
                    existing.setTipoReaccion(tipoReaccion);
                    return reactionRepositoryPort.save(existing);
                })
                .orElse(null);
    }

    private void incrementTargetCounter(String objetivoId, String tipoObjetivo, int delta) {
        try {
            if ("POST".equalsIgnoreCase(tipoObjetivo)) {
                postRepositoryPort.findById(objetivoId).ifPresent(post -> {
                    if (post.getEstadisticas() == null) {
                        post.setEstadisticas(new com.comunidev.comunidevbackend.post.domain.Post.PostEstadisticas());
                    }
                    int current = post.getEstadisticas().getReaccionesCount() != null
                            ? post.getEstadisticas().getReaccionesCount() : 0;
                    post.getEstadisticas().setReaccionesCount(Math.max(0, current + delta));
                    postRepositoryPort.save(post);
                });
            } else if ("COMMENT".equalsIgnoreCase(tipoObjetivo)) {
                commentRepositoryPort.findById(objetivoId).ifPresent(comment -> {
                    int current = comment.getReaccionesCount() != null ? comment.getReaccionesCount() : 0;
                    comment.setReaccionesCount(Math.max(0, current + delta));
                    commentRepositoryPort.save(comment);
                });
            }
        } catch (Exception ignored) {
            // counter update is best-effort, don't fail the reaction
        }
    }
}
