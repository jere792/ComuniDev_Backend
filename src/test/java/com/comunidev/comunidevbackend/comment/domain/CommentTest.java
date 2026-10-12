package com.comunidev.comunidevbackend.comment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommentTest {

    @Test
    @DisplayName("un comentario raiz no tiene parentCommentId")
    void comentarioRaizSinPadre() {
        Comment comment = Comment.create("autor-1", "post-1", "POST", null, "buen aporte", null);

        assertNull(comment.getParentCommentId());
        assertEquals("post-1", comment.getContenidoId());
        assertEquals("POST", comment.getTipoContenido());
        assertEquals("APROBADO", comment.getEstadoModeracion());
        assertEquals(0, comment.getReaccionesCount());
    }

    @Test
    @DisplayName("una respuesta si lleva parentCommentId")
    void respuestaConPadre() {
        Comment reply = Comment.create("autor-2", "post-1", "POST", "comment-1", "gracias", null);

        assertEquals("comment-1", reply.getParentCommentId());
    }

    @Test
    @DisplayName("imagenes nulas se normalizan a lista vacia, no a null")
    void imagenesNulasSeNormalizan() {
        Comment comment = Comment.create("autor-1", "post-1", "POST", null, "texto", null);

        assertNotNull(comment.getImagenes());
        assertTrue(comment.getImagenes().isEmpty());
    }

    @Test
    @DisplayName("imagenes entregadas se conservan")
    void imagenesEntregadasSeConservan() {
        Comment comment = Comment.create("autor-1", "post-1", "POST", null, "texto",
                List.of("a.png", "b.png"));

        assertEquals(2, comment.getImagenes().size());
    }
}
