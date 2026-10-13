package com.comunidev.comunidevbackend.company.application.dto;

import com.comunidev.comunidevbackend.company.domain.Company;

/**
 * Petición de {@code createCompany} / {@code updateCompany}.
 *
 * <p>Todos los campos son anulables: en un update los {@code null} significan
 * "no tocar ese campo", igual que en el resto del proyecto.
 *
 * <p>{@code recruiterId} solo lo usa el create — una empresa no se transfiere
 * de dueño.
 */
public record CompanyRequest(
        String recruiterId,
        String nombre,
        String descripcion,
        String sector,
        String sitioWeb,
        String logoUrl,
        String bannerUrl,
        String ruc,
        String fechaCreacion,
        Company.Ubicacion ubicacion,
        Company.Contacto contacto,
        Company.Redes redes) {
}
