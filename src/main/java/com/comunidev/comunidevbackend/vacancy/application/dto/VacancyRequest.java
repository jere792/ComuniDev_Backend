package com.comunidev.comunidevbackend.vacancy.application.dto;

import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;

import java.util.List;

/**
 * Petición de {@code createVacancy} / {@code updateVacancy}.
 *
 * <p>{@code fechaLimite} llega como texto ISO-8601 (ej. {@code 2026-12-31})
 * porque así lo expone el esquema GraphQL; el servicio lo convierte a
 * {@link java.time.Instant} y valida que no sea pasada (RN-22).
 */
public record VacancyRequest(
        String companyId,
        String recruiterId,
        String titulo,
        String descripcion,
        List<String> responsabilidades,
        List<String> requisitos,
        List<String> tecnologias,
        String nivelExperiencia,
        String modalidad,
        String tipoContrato,
        String ubicacion,
        Vacancy.Remuneracion remuneracion,
        Integer vacantesDisponibles,
        String fechaLimite,
        /** Solo lo aplica updateVacancy: el create siempre registra ACTIVA (CA-HU-40). */
        String estado) {
}
