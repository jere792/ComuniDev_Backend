package com.comunidev.comunidevbackend.vacancy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VacancyTest {

    @Test
    @DisplayName("create() deja la vacante ACTIVA con los contadores en cero")
    void createAplicaDefaults() {
        Vacancy vacancy = Vacancy.create("company-1", "recruiter-1", "Backend Developer");

        assertEquals("company-1", vacancy.getCompanyId());
        assertEquals("recruiter-1", vacancy.getRecruiterId());
        assertEquals("Backend Developer", vacancy.getTitulo());
        assertEquals(Vacancy.ESTADO_ACTIVA, vacancy.getEstado());
        assertEquals(false, vacancy.getDestacada());
        assertEquals(0, vacancy.getPostulacionesCount());
        assertNotNull(vacancy.getCreatedAt());
        assertNotNull(vacancy.getUpdatedAt());
    }

    @Test
    @DisplayName("estaVencida() es false sin fecha limite y depende de la fecha despues")
    void estaVencidaSegunFechaLimite() {
        Vacancy vacancy = Vacancy.create("c", "r", "t");
        assertFalse(vacancy.estaVencida(Instant.now()));

        vacancy.setFechaLimite(Instant.now().minus(1, ChronoUnit.DAYS));
        assertTrue(vacancy.estaVencida(Instant.now()));

        vacancy.setFechaLimite(Instant.now().plus(1, ChronoUnit.DAYS));
        assertFalse(vacancy.estaVencida(Instant.now()));
    }

    @Test
    @DisplayName("marcarVencidaSiAplica solo pasa a VENCIDA si esta ACTIVA y vencida")
    void marcarVencidaSoloSiActivaYVencida() {
        Instant pasado = Instant.now().minus(1, ChronoUnit.DAYS);

        Vacancy activaVencida = Vacancy.create("c", "r", "t");
        activaVencida.setFechaLimite(pasado);
        assertTrue(activaVencida.marcarVencidaSiAplica(Instant.now()));
        assertEquals(Vacancy.ESTADO_VENCIDA, activaVencida.getEstado());

        // Una cerrada manualmente no debe ser tocada por el sistema (HU-41).
        Vacancy cerrada = Vacancy.create("c", "r", "t");
        cerrada.setEstado(Vacancy.ESTADO_CERRADA);
        cerrada.setFechaLimite(pasado);
        assertFalse(cerrada.marcarVencidaSiAplica(Instant.now()));
        assertEquals(Vacancy.ESTADO_CERRADA, cerrada.getEstado());

        Vacancy vigente = Vacancy.create("c", "r", "t");
        vigente.setFechaLimite(Instant.now().plus(1, ChronoUnit.DAYS));
        assertFalse(vigente.marcarVencidaSiAplica(Instant.now()));
        assertEquals(Vacancy.ESTADO_ACTIVA, vigente.getEstado());
    }
}
