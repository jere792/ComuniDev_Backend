package com.comunidev.comunidevbackend.comment.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "comments")
public class Comment extends AggregateRoot<String> {
    private String autorId;
    private String contenidoId;
    private String tipoContenido;
    private String parentCommentId;
    private String texto;
    private List<String> imagenes = new ArrayList<>();
    private String estadoModeracion = "APROBADO";
    private Integer reaccionesCount = 0;
    private Instant createdAt;
    private Instant updatedAt;

    public static Comment create(String autorId, String contenidoId, String tipoContenido,
                                 String parentCommentId, String texto, List<String> imagenes) {
        Comment comment = new Comment();
        comment.setAutorId(autorId);
        comment.setContenidoId(contenidoId);
        comment.setTipoContenido(tipoContenido);
        comment.setParentCommentId(parentCommentId);
        comment.setTexto(texto);
        comment.setImagenes(imagenes != null ? imagenes : new ArrayList<>());
        comment.setCreatedAt(Instant.now());
        comment.setUpdatedAt(Instant.now());
        return comment;
    }
}
