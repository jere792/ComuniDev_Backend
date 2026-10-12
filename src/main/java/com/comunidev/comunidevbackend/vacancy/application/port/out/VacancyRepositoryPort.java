package com.comunidev.comunidevbackend.vacancy.application.port.out;

import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyRepositoryPort {
    Vacancy save(Vacancy vacancy);
    Optional<Vacancy> findById(String id);

    /** HU-42: listado público, por defecto solo ACTIVA (CA-HU-42). */
    List<Vacancy> findAllByEstado(String estado);

    /** Vista del reclutador: todas las vacantes de su empresa, en cualquier estado. */
    List<Vacancy> findAllByCompanyId(String companyId);

    /** "Mis Ofertas" (HU-44): las vacantes que publicó ese reclutador. */
    List<Vacancy> findAllByRecruiterId(String recruiterId);

    /** RN-31: mantiene Company.vacantesActivasCount. */
    int countByCompanyIdAndEstado(String companyId, String estado);
}
