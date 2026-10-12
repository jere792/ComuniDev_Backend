package com.comunidev.comunidevbackend.company.application.port.in;

import com.comunidev.comunidevbackend.company.application.dto.CompanyRequest;
import com.comunidev.comunidevbackend.company.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyUseCase {
    List<Company> getCompanies();
    Company getCompany(String id);

    /** "Mi empresa". Vacía si el reclutador todavía no creó ninguna. */
    Optional<Company> getMyCompany(String recruiterId);

    Company createCompany(CompanyRequest request);
    Company updateCompany(String id, CompanyRequest request);
}
