package com.comunidev.comunidevbackend.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostTest {

    @Test
    @DisplayName("create() guarda los datos tal cual se reciben")
    void createGuardaLosDatosRecibidos() {
        Post.PostContenido contenido = new Post.PostContenido();
        contenido.setTexto("Hola ComuniDev");

        Post post = Post.create("autor-1", contenido, List.of("java", "spring"),
                "tecnologia", "PUBLICO");

        assertEquals("autor-1", post.getAutorId());
        assertEquals("Hola ComuniDev", post.getContenido().getTexto());
        assertEquals(List.of("java", "spring"), post.getEtiquetas());
        assertEquals("tecnologia", post.getCategoria());
        assertEquals("PUBLICO", post.getVisibilidad());
        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getUpdatedAt());
    }

    @Test
    @DisplayName("create() con argumentos nulos aplica defaults en vez de fallar")
    void createConNulosAplicaDefaults() {
        Post post = Post.create("autor-1", null, null, null, null);

        assertNotNull(post.getContenido(), "el contenido no debe quedar nulo");
        assertNotNull(post.getEtiquetas(), "las etiquetas no deben quedar nulas");
        assertTrue(post.getEtiquetas().isEmpty());
        assertEquals("PUBLICO", post.getVisibilidad(),
                "sin visibilidad explicita debe quedar PUBLICO");
    }

    @Test
    @DisplayName("una entidad nueva arranca con estadisticas y moderacion por defecto")
    void valoresPorDefecto() {
        Post post = Post.create("autor-1", null, null, null, null);

        assertEquals("TEXTO", post.getTipo());
        assertEquals("APROBADO", post.getEstadoModeracion());
        assertNotNull(post.getEstadisticas());
        assertEquals(0, post.getEstadisticas().getReaccionesCount());
        assertEquals(0, post.getEstadisticas().getComentariosCount());
        assertEquals(0, post.getEstadisticas().getVistas());
    }
}
