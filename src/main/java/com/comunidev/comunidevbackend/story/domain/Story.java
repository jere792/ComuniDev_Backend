package com.comunidev.comunidevbackend.story.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "stories")
public class Story extends AggregateRoot<String> {
    private String autorId;
    private StoryContenido contenido = new StoryContenido();
    private String visibilidad = "PUBLICO";
    private Instant fechaExpiracion;
    private Integer vistasCount = 0;
    private Integer reaccionesCount = 0;
    private String estado = "ACTIVO";
    private Instant createdAt;

    @Getter
    @Setter
    public static class StoryContenido {
        private String texto;
        private String imagenUrl;
        private String videoUrl;
        private StoryMusica musica;
    }

    @Getter
    @Setter
    public static class StoryMusica {
        private String trackId;
        private String trackName;
        private String artistName;
        private String coverUrl;
        private String previewUrl;
        private String musicMode = "cover"; // cover | audio | lyrics
        private String lyricsText;
        private Double lyricsPosX = 50.0; // percentage 0-100
        private Double lyricsPosY = 50.0; // percentage 0-100
        private Double coverPosX = 50.0; // percentage 0-100
        private Double coverPosY = 50.0; // percentage 0-100
        private Double lyricsScale = 1.0;
        private Double coverScale = 1.0;
    }

    public static Story create(String autorId, StoryContenido contenido, String visibilidad) {
        Story story = new Story();
        story.setAutorId(autorId);
        story.setContenido(contenido != null ? contenido : new StoryContenido());
        story.setVisibilidad(visibilidad != null ? visibilidad : "PUBLICO");
        story.setCreatedAt(Instant.now());
        story.setFechaExpiracion(story.getCreatedAt().plusSeconds(24 * 60 * 60));
        return story;
    }
}
