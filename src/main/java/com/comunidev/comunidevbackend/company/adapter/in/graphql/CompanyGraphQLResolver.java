package com.comunidev.comunidevbackend.company.adapter.in.graphql;

import com.comunidev.comunidevbackend.company.application.dto.CompanyRequest;
import com.comunidev.comunidevbackend.company.application.port.in.CompanyUseCase;
import com.comunidev.comunidevbackend.company.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyGraphQLResolver {

    private final CompanyUseCase companyUseCase;

    @QueryMapping
    public List<Company> companies() {
        return companyUseCase.getCompanies();
    }

    @QueryMapping
    public Company company(@Argument String id) {
        return companyUseCase.getCompany(id);
    }

    /** "Mi empresa"; null si el reclutador todavía no creó ninguna. */
    @QueryMapping
    public Company myCompany(@Argument String recruiterId) {
        return companyUseCase.getMyCompany(recruiterId).orElse(null);
    }

    @MutationMapping
    public Company createCompany(
            @Argument String recruiterId,
            @Argument String nombre,
            @Argument String descripcion,
            @Argument String sector,
            @Argument String sitioWeb,
            @Argument String logoUrl,
            @Argument String bannerUrl,
            @Argument String ruc,
            @Argument String fechaCreacion,
            @Argument Company.Ubicacion ubicacion,
            @Argument Company.Contacto contacto,
            @Argument Company.Redes redes) {

        return companyUseCase.createCompany(new CompanyRequest(
                recruiterId, nombre, descripcion, sector, sitioWeb, logoUrl, bannerUrl,
                ruc, fechaCreacion, ubicacion, contacto, redes));
    }

    @MutationMapping
    public Company updateCompany(
            @Argument String id,
            @Argument String nombre,
            @Argument String descripcion,
            @Argument String sector,
            @Argument String sitioWeb,
            @Argument String logoUrl,
            @Argument String bannerUrl,
            @Argument String ruc,
            @Argument String fechaCreacion,
            @Argument Company.Ubicacion ubicacion,
            @Argument Company.Contacto contacto,
            @Argument Company.Redes redes) {

        // recruiterId en null: una empresa no cambia de dueño (solo el create lo fija).
        return companyUseCase.updateCompany(id, new CompanyRequest(
                null, nombre, descripcion, sector, sitioWeb, logoUrl, bannerUrl,
                ruc, fechaCreacion, ubicacion, contacto, redes));
    }
}
