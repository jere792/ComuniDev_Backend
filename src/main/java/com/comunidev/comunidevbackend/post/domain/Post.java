package com.comunidev.comunidevbackend.post.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "posts")
public class Post extends AggregateRoot<String> {
    private String autorId;
    private String tipo = "TEXTO";
    private PostContenido contenido = new PostContenido();
    private List<String> etiquetas = new ArrayList<>();
    private String categoria;
    private String visibilidad = "PUBLICO";
    private String estadoModeracion = "APROBADO";
    private PostEstadisticas estadisticas = new PostEstadisticas();
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    public static class PostContenido {
        private String texto;
        private List<String> imagenes = new ArrayList<>();
        private List<String> videos = new ArrayList<>();
        private List<String> archivos = new ArrayList<>();
        private List<String> bloquesCodigo = new ArrayList<>();
        private List<String> enlaces = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class PostEstadisticas {
        private Integer vistas = 0;
        private Integer reaccionesCount = 0;
        private Integer comentariosCount = 0;
        private Integer compartidosCount = 0;
        private Integer guardadosCount = 0;
    }

    public static Post create(String autorId, PostContenido contenido, List<String> etiquetas,
                              String categoria, String visibilidad) {
        Post post = new Post();
        post.setAutorId(autorId);
        post.setContenido(contenido != null ? contenido : new PostContenido());
        post.setEtiquetas(etiquetas != null ? etiquetas : new ArrayList<>());
        post.setCategoria(categoria);
        post.setVisibilidad(visibilidad != null ? visibilidad : "PUBLICO");
        post.setCreatedAt(Instant.now());
        post.setUpdatedAt(Instant.now());
        return post;
    }
}
