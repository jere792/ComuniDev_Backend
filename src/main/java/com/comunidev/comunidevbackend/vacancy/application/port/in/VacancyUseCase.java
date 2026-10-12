package com.comunidev.comunidevbackend.vacancy.application.port.in;

import com.comunidev.comunidevbackend.vacancy.application.dto.VacancyRequest;
import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;

import java.util.List;

public interface VacancyUseCase {

    /**
     * Listado público (HU-42). Si no llega estado se usan solo las ACTIVAS,
     * para no filtrar vacantes cerradas o vencidas (CA-HU-42).
     */
    List<Vacancy> getVacancies(String estado);

    Vacancy getVacancy(String id);

    /** Vacantes de una empresa en cualquier estado: la usa el reclutador (HU-44). */
    List<Vacancy> getVacanciesByCompany(String companyId);

    /** "Mis Ofertas": las que publicó ese reclutador, en cualquier estado. */
    List<Vacancy> getVacanciesByRecruiter(String recruiterId);

    Vacancy createVacancy(VacancyRequest request);

    Vacancy updateVacancy(String id, VacancyRequest request);
}
