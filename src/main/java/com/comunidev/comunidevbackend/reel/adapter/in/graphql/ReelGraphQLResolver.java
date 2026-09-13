package com.comunidev.comunidevbackend.reel.adapter.in.graphql;

import com.comunidev.comunidevbackend.reel.application.port.out.ReelRepositoryPort;
import com.comunidev.comunidevbackend.reel.domain.Reel;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReelGraphQLResolver {

    private final ReelRepositoryPort reelRepositoryPort;

    @QueryMapping
    public List<Reel> reels() {
        return reelRepositoryPort.findAll();
    }

    @QueryMapping
    public Reel reel(@Argument String id) {
        return reelRepositoryPort.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Reel> reelsByUser(@Argument String userId) {
        return reelRepositoryPort.findByAutorId(userId);
    }

    @MutationMapping
    public Reel createReel(
            @Argument String autorId,
            @Argument String videoUrl,
            @Argument String portadaUrl,
            @Argument String descripcion,
            @Argument List<String> etiquetas,
            @Argument Reel.ReelMusica musica,
            @Argument Integer duracionSegundos,
            @Argument String visibilidad) {

        Reel reel = Reel.create(autorId, videoUrl, portadaUrl, descripcion, etiquetas, musica, duracionSegundos, visibilidad);
        return reelRepositoryPort.save(reel);
    }

    @MutationMapping
    public Reel updateReel(
            @Argument String id,
            @Argument String descripcion,
            @Argument List<String> etiquetas,
            @Argument String visibilidad) {

        return reelRepositoryPort.findById(id)
                .map(reel -> {
                    if (descripcion != null) reel.setDescripcion(descripcion);
                    if (etiquetas != null) reel.setEtiquetas(etiquetas);
                    if (visibilidad != null) reel.setVisibilidad(visibilidad);
                    reel.setUpdatedAt(Instant.now());
                    return reelRepositoryPort.save(reel);
                })
                .orElse(null);
    }

    @MutationMapping
    public Boolean deleteReel(@Argument String id) {
        try {
            reelRepositoryPort.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
