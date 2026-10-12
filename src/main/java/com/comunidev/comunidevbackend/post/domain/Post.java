package com.comunidev.comunidevbackend.post.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "posts")
// Feed principal: "mis posts" y "posts de quienes sigo", siempre por fecha.
@CompoundIndex(name = "idx_posts_autor_created", def = "{'autorId': 1, 'createdAt': -1}")
// Listado general y feed sin seguir a nadie.
@CompoundIndex(name = "idx_posts_created", def = "{'createdAt': -1}")
public class Post extends AggregateRoot<String> {
    @Indexed
    private String autorId;
    private String tipo = "TEXTO";
    private PostContenido contenido = new PostContenido();
    private List<String> etiquetas = new ArrayList<>();
    @Indexed
    private String categoria;
    private String visibilidad = "PUBLICO";
    private String estadoModeracion = "APROBADO";
    private PostEstadisticas estadisticas = new PostEstadisticas();
    @Indexed
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
