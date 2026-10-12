package com.comunidev.comunidevbackend.company.application.port.out;

import com.comunidev.comunidevbackend.company.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepositoryPort {
    Company save(Company company);
    Optional<Company> findById(String id);
    List<Company> findAll();

    /** "Mi empresa": la que administra ese reclutador. */
    Optional<Company> findByRecruiterId(String recruiterId);

    /** RN-31: el contador se recalcula al publicar, vencer o cerrar vacantes. */
    void setVacantesActivasCount(String companyId, int count);
}
