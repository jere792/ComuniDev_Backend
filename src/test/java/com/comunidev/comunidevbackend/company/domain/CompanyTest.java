package com.comunidev.comunidevbackend.company.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CompanyTest {

    @Test
    @DisplayName("create() deja la empresa PENDIENTE de verificacion y sin vacantes")
    void createAplicaDefaults() {
        Company company = Company.create("recruiter-1", "DevSolutions Perú");

        assertEquals("recruiter-1", company.getRecruiterId());
        assertEquals("DevSolutions Perú", company.getNombre());
        assertEquals(Company.ESTADO_PENDIENTE, company.getEstadoVerificacion());
        assertEquals(0, company.getVacantesActivasCount());
        assertNotNull(company.getCreatedAt());
        assertNotNull(company.getUpdatedAt());
    }
}
