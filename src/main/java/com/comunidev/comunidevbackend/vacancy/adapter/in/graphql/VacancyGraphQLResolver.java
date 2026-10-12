package com.comunidev.comunidevbackend.vacancy.adapter.in.graphql;

import com.comunidev.comunidevbackend.vacancy.application.dto.VacancyRequest;
import com.comunidev.comunidevbackend.vacancy.application.port.in.VacancyUseCase;
import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VacancyGraphQLResolver {

    private final VacancyUseCase vacancyUseCase;

    /** HU-42: sin estado se listan solo las ACTIVAS (CA-HU-42). */
    @QueryMapping
    public List<Vacancy> vacancies(@Argument String estado) {
        return vacancyUseCase.getVacancies(estado);
    }

    @QueryMapping
    public Vacancy vacancy(@Argument String id) {
        return vacancyUseCase.getVacancy(id);
    }

    /** HU-44: el reclutador ve todas las vacantes de su empresa. */
    @QueryMapping
    public List<Vacancy> vacanciesByCompany(@Argument String companyId) {
        return vacancyUseCase.getVacanciesByCompany(companyId);
    }

    /** HU-44: "Mis Ofertas", las que publicó ese reclutador. */
    @QueryMapping
    public List<Vacancy> vacanciesByRecruiter(@Argument String recruiterId) {
        return vacancyUseCase.getVacanciesByRecruiter(recruiterId);
    }

    @MutationMapping
    public Vacancy createVacancy(
            @Argument String companyId,
            @Argument String recruiterId,
            @Argument String titulo,
            @Argument String descripcion,
            @Argument List<String> responsabilidades,
            @Argument List<String> requisitos,
            @Argument List<String> tecnologias,
            @Argument String nivelExperiencia,
            @Argument String modalidad,
            @Argument String tipoContrato,
            @Argument String ubicacion,
            @Argument Vacancy.Remuneracion remuneracion,
            @Argument Integer vacantesDisponibles,
            @Argument String fechaLimite) {

        return vacancyUseCase.createVacancy(new VacancyRequest(
                companyId, recruiterId, titulo, descripcion, responsabilidades, requisitos,
                tecnologias, nivelExperiencia, modalidad, tipoContrato, ubicacion,
                remuneracion, vacantesDisponibles, fechaLimite, null));
    }

    @MutationMapping
    public Vacancy updateVacancy(
            @Argument String id,
            @Argument String titulo,
            @Argument String descripcion,
            @Argument List<String> responsabilidades,
            @Argument List<String> requisitos,
            @Argument List<String> tecnologias,
            @Argument String nivelExperiencia,
            @Argument String modalidad,
            @Argument String tipoContrato,
            @Argument String ubicacion,
            @Argument Vacancy.Remuneracion remuneracion,
            @Argument Integer vacantesDisponibles,
            @Argument String fechaLimite,
            @Argument String estado) {

        // companyId y recruiterId no van aquí: una vacante no se puede mover de
        // empresa ni de reclutador. El esquema tampoco los expone en updateVacancy.
        return vacancyUseCase.updateVacancy(id, new VacancyRequest(
                null, null, titulo, descripcion, responsabilidades, requisitos,
                tecnologias, nivelExperiencia, modalidad, tipoContrato, ubicacion,
                remuneracion, vacantesDisponibles, fechaLimite, estado));
    }
}
