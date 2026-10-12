package com.comunidev.comunidevbackend.reaction.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReactionTest {

    @Test
    @DisplayName("create() fija el tipo de reaccion y la fecha de creacion")
    void createFijaTipoYFecha() {
        Reaction reaction = Reaction.create("user-1", "post-1", "POST", TipoReaccion.LIKE);

        assertEquals("user-1", reaction.getUsuarioId());
        assertEquals("post-1", reaction.getObjetivoId());
        assertEquals("POST", reaction.getTipoObjetivo());
        assertEquals(TipoReaccion.LIKE, reaction.getTipoReaccion());
        assertNotNull(reaction.getCreatedAt());
    }

    @Test
    @DisplayName("cambiar la reaccion actualiza solo el tipo, no el objetivo")
    void cambiarTipoConservaObjetivo() {
        Reaction reaction = Reaction.create("user-1", "post-1", "POST", TipoReaccion.LIKE);
        reaction.setTipoReaccion(TipoReaccion.LOVE);

        assertEquals(TipoReaccion.LOVE, reaction.getTipoReaccion());
        assertEquals("post-1", reaction.getObjetivoId(), "el objetivo no debe cambiar");
        assertEquals("user-1", reaction.getUsuarioId());
    }
}
