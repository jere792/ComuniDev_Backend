package com.comunidev.comunidevbackend.category.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryTest {

    @Test
    @DisplayName("create() deja la categoria activa y en orden 0 por defecto")
    void createAplicaDefaults() {
        Category category = Category.create("user-1", "Backend");

        assertEquals("user-1", category.getUserId());
        assertEquals("Backend", category.getNombre());
        assertEquals(0, category.getOrden());
        assertTrue(category.getActiva());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
    }

    @Test
    @DisplayName("el orden se puede reasignar despues de crearla")
    void sePuedeReasignarOrden() {
        Category category = Category.create("user-1", "Backend");
        category.setOrden(5);

        assertEquals(5, category.getOrden());
    }
}
