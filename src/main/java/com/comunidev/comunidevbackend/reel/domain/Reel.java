package com.comunidev.comunidevbackend.reel.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "reels")
public class Reel extends AggregateRoot<String> {
    private String autorId;
    private String videoUrl;
    private String portadaUrl;
    private String descripcion;
    private List<String> etiquetas = new ArrayList<>();
    private ReelMusica musica;
    private Integer duracionSegundos;
    private String visibilidad = "PUBLICO";
    private String estadoModeracion = "APROBADO";
    private ReelEstadisticas estadisticas = new ReelEstadisticas();
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    public static class ReelMusica {
        private String titulo;
        private String artista;
    }

    @Getter
    @Setter
    public static class ReelEstadisticas {
        private Integer vistas = 0;
        private Integer reaccionesCount = 0;
        private Integer comentariosCount = 0;
        private Integer compartidosCount = 0;
        private Integer guardadosCount = 0;
    }

    public static Reel create(String autorId, String videoUrl, String portadaUrl, String descripcion,
                              List<String> etiquetas, ReelMusica musica, Integer duracionSegundos, String visibilidad) {
        Reel reel = new Reel();
        reel.setAutorId(autorId);
        reel.setVideoUrl(videoUrl);
        reel.setPortadaUrl(portadaUrl);
        reel.setDescripcion(descripcion);
        reel.setEtiquetas(etiquetas != null ? etiquetas : new ArrayList<>());
        reel.setMusica(musica);
        reel.setDuracionSegundos(duracionSegundos);
        reel.setVisibilidad(visibilidad != null ? visibilidad : "PUBLICO");
        reel.setCreatedAt(Instant.now());
        reel.setUpdatedAt(Instant.now());
        return reel;
    }
}
