package com.comunidev.comunidevbackend.vacancy.application.service;

import com.comunidev.comunidevbackend.company.application.port.out.CompanyRepositoryPort;
import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import com.comunidev.comunidevbackend.vacancy.application.dto.VacancyRequest;
import com.comunidev.comunidevbackend.vacancy.application.port.in.VacancyUseCase;
import com.comunidev.comunidevbackend.vacancy.application.port.out.VacancyRepositoryPort;
import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class VacancyService implements VacancyUseCase {

    private final VacancyRepositoryPort vacancyRepositoryPort;
    private final CompanyRepositoryPort companyRepositoryPort;

    @Override
    public List<Vacancy> getVacancies(String estado) {
        String filtro = isBlank(estado) ? Vacancy.ESTADO_POR_DEFECTO : estado;
        List<Vacancy> vacancies = expirarVencidas(vacancyRepositoryPort.findAllByEstado(filtro));
        // CA-HU-42: una vacante que acaba de vencer ya no se muestra como activa.
        return vacancies.stream().filter(v -> filtro.equals(v.getEstado())).toList();
    }

    @Override
    public Vacancy getVacancy(String id) {
        Vacancy vacancy = vacancyRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy", "id", id));
        if (vacancy.marcarVencidaSiAplica(Instant.now())) {
            vacancyRepositoryPort.save(vacancy);
            recalcularVacantesActivas(vacancy.getCompanyId());
        }
        return vacancy;
    }

    @Override
    public List<Vacancy> getVacanciesByCompany(String companyId) {
        // El reclutador necesita ver también las cerradas y vencidas (HU-44).
        return expirarVencidas(vacancyRepositoryPort.findAllByCompanyId(companyId));
    }

    @Override
    public List<Vacancy> getVacanciesByRecruiter(String recruiterId) {
        return expirarVencidas(vacancyRepositoryPort.findAllByRecruiterId(recruiterId));
    }

    @Override
    public Vacancy createVacancy(VacancyRequest request) {
        // Con la empresa opcional, el reclutador es la única atribución de la vacante.
        if (isBlank(request.recruiterId())) {
            throw new BusinessException("La vacante necesita un reclutador que la publique");
        }

        // companyId es opcional: un reclutador independiente publica sin empresa
        // (nullable en el esquema desde el diseño original). Si sí viene, debe existir.
        if (!isBlank(request.companyId()) && companyRepositoryPort.findById(request.companyId()).isEmpty()) {
            throw new ResourceNotFoundException("Company", "id", request.companyId());
        }

        Vacancy vacancy = Vacancy.create(request.companyId(), request.recruiterId(), request.titulo());
        apply(vacancy, request);
        // CA-HU-40: se registra como ACTIVA, así que debe cumplir todo lo que exige.
        validarParaActiva(vacancy);

        Vacancy saved = vacancyRepositoryPort.save(vacancy);
        recalcularVacantesActivas(saved.getCompanyId());
        return saved;
    }

    @Override
    public Vacancy updateVacancy(String id, VacancyRequest request) {
        Vacancy vacancy = getVacancy(id);
        apply(vacancy, request);
        // HU-41: el reclutador puede activar o cerrar manualmente.
        if (!isBlank(request.estado())) {
            vacancy.setEstado(request.estado());
        }
        if (Vacancy.ESTADO_ACTIVA.equals(vacancy.getEstado())) {
            validarParaActiva(vacancy);
        }
        vacancy.setUpdatedAt(Instant.now());

        Vacancy saved = vacancyRepositoryPort.save(vacancy);
        recalcularVacantesActivas(saved.getCompanyId());
        return saved;
    }

    /**
     * 03-ENTIDADES.md 6.1: "Una vacante activa requiere título, descripción,
     * modalidad, tecnologías, fecha límite y ubicación o condición remota".
     * RN-22: la fecha límite no puede ser anterior a la fecha actual.
     *
     * <p>La empresa se quitó de los requisitos: un reclutador independiente
     * publica sin ella, y el esquema la tenía nullable desde el diseño original.
     */
    private void validarParaActiva(Vacancy vacancy) {
        if (isBlank(vacancy.getTitulo())) {
            throw new BusinessException("La vacante activa requiere título");
        }
        if (isBlank(vacancy.getDescripcion())) {
            throw new BusinessException("La vacante activa requiere descripción");
        }
        if (isBlank(vacancy.getModalidad())) {
            throw new BusinessException("La vacante activa requiere modalidad");
        }
        if (vacancy.getTecnologias() == null || vacancy.getTecnologias().isEmpty()) {
            throw new BusinessException("La vacante activa requiere al menos una tecnología");
        }
        if (vacancy.getFechaLimite() == null) {
            throw new BusinessException("La vacante activa requiere fecha límite");
        }
        if (vacancy.getFechaLimite().isBefore(Instant.now())) {
            throw new BusinessException("La fecha límite no puede ser anterior a la fecha actual");
        }
        boolean esRemoto = Vacancy.MODALIDAD_REMOTO.equalsIgnoreCase(vacancy.getModalidad());
        if (isBlank(vacancy.getUbicacion()) && !esRemoto) {
            throw new BusinessException("La vacante activa requiere ubicación o condición remota");
        }
    }

    /** RN-21: las vacantes activas ya vencidas pasan a VENCIDA y dejan de contarse. */
    private List<Vacancy> expirarVencidas(List<Vacancy> vacancies) {
        Instant ahora = Instant.now();
        boolean cambio = false;
        for (Vacancy vacancy : vacancies) {
            if (vacancy.marcarVencidaSiAplica(ahora)) {
                vacancyRepositoryPort.save(vacancy);
                cambio = true;
            }
        }
        if (cambio) {
            vacancies.stream().map(Vacancy::getCompanyId).distinct()
                    .forEach(this::recalcularVacantesActivas);
        }
        return vacancies;
    }

    private void recalcularVacantesActivas(String companyId) {
        if (companyId == null) return;
        int activas = vacancyRepositoryPort.countByCompanyIdAndEstado(companyId, Vacancy.ESTADO_ACTIVA);
        companyRepositoryPort.setVacantesActivasCount(companyId, activas);
    }

    /** Aplica solo los campos enviados, para que un update parcial no borre datos. */
    private void apply(Vacancy vacancy, VacancyRequest request) {
        if (request.companyId() != null) vacancy.setCompanyId(request.companyId());
        if (request.recruiterId() != null) vacancy.setRecruiterId(request.recruiterId());
        if (request.titulo() != null) vacancy.setTitulo(request.titulo());
        if (request.descripcion() != null) vacancy.setDescripcion(request.descripcion());
        if (request.responsabilidades() != null) {
            vacancy.setResponsabilidades(new ArrayList<>(request.responsabilidades()));
        }
        if (request.requisitos() != null) vacancy.setRequisitos(new ArrayList<>(request.requisitos()));
        if (request.tecnologias() != null) vacancy.setTecnologias(new ArrayList<>(request.tecnologias()));
        if (request.nivelExperiencia() != null) vacancy.setNivelExperiencia(request.nivelExperiencia());
        if (request.modalidad() != null) vacancy.setModalidad(request.modalidad());
        if (request.tipoContrato() != null) vacancy.setTipoContrato(request.tipoContrato());
        if (request.ubicacion() != null) vacancy.setUbicacion(request.ubicacion());
        if (request.remuneracion() != null) vacancy.setRemuneracion(request.remuneracion());
        if (request.vacantesDisponibles() != null) vacancy.setVacantesDisponibles(request.vacantesDisponibles());
        if (request.fechaLimite() != null) vacancy.setFechaLimite(parseFechaLimite(request.fechaLimite()));
    }

    /** Acepta "2026-12-31" o un ISO-8601 completo. */
    private Instant parseFechaLimite(String fechaLimite) {
        try {
            return fechaLimite.length() <= 10
                    ? LocalDate.parse(fechaLimite).atStartOfDay(ZoneOffset.UTC).toInstant()
                    : Instant.parse(fechaLimite);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Fecha límite inválida: " + fechaLimite);
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
