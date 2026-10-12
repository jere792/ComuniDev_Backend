package com.comunidev.comunidevbackend.company.application.service;

import com.comunidev.comunidevbackend.company.application.dto.CompanyRequest;
import com.comunidev.comunidevbackend.company.application.port.in.CompanyUseCase;
import com.comunidev.comunidevbackend.company.application.port.out.CompanyRepositoryPort;
import com.comunidev.comunidevbackend.company.domain.Company;
import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class CompanyService implements CompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;

    @Override
    public List<Company> getCompanies() {
        return companyRepositoryPort.findAll();
    }

    @Override
    public Company getCompany(String id) {
        return companyRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
    }

    @Override
    public Optional<Company> getMyCompany(String recruiterId) {
        return companyRepositoryPort.findByRecruiterId(recruiterId);
    }

    @Override
    public Company createCompany(CompanyRequest request) {
        // Una empresa por reclutador: la pantalla es "Mi empresa" y
        // findByRecruiterId devolvería más de un resultado si se permitieran varias.
        if (isBlank(request.recruiterId())) {
            throw new BusinessException("La empresa necesita un reclutador responsable");
        }
        if (companyRepositoryPort.findByRecruiterId(request.recruiterId()).isPresent()) {
            throw new BusinessException("Ese reclutador ya tiene una empresa registrada");
        }

        // 03-ENTIDADES.md 1.4: para publicar vacantes necesita nombre,
        // correo de contacto y ubicación geográfica.
        if (isBlank(request.nombre())) {
            throw new BusinessException("El nombre de la empresa es obligatorio");
        }
        if (request.contacto() == null || isBlank(request.contacto().getEmail())) {
            throw new BusinessException("El correo de contacto es obligatorio");
        }
        if (request.ubicacion() == null || isBlank(request.ubicacion().getPais())) {
            throw new BusinessException("La ubicación (país) es obligatoria");
        }

        Company company = Company.create(request.recruiterId(), request.nombre());
        apply(company, request);
        return companyRepositoryPort.save(company);
    }

    @Override
    public Company updateCompany(String id, CompanyRequest request) {
        Company company = getCompany(id);
        apply(company, request);
        company.setUpdatedAt(Instant.now());
        return companyRepositoryPort.save(company);
    }

    /** Aplica solo los campos enviados, para que un update parcial no borre datos. */
    private void apply(Company company, CompanyRequest request) {
        if (request.nombre() != null) company.setNombre(request.nombre());
        if (request.descripcion() != null) company.setDescripcion(request.descripcion());
        if (request.sector() != null) company.setSector(request.sector());
        if (request.sitioWeb() != null) company.setSitioWeb(request.sitioWeb());
        if (request.logoUrl() != null) company.setLogoUrl(request.logoUrl());
        if (request.bannerUrl() != null) company.setBannerUrl(request.bannerUrl());
        if (request.ubicacion() != null) company.setUbicacion(request.ubicacion());
        if (request.contacto() != null) company.setContacto(request.contacto());
        if (request.redes() != null) company.setRedes(request.redes());
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
