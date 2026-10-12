package com.comunidev.comunidevbackend.vacancy.application.service;

import com.comunidev.comunidevbackend.company.application.port.out.CompanyRepositoryPort;
import com.comunidev.comunidevbackend.company.domain.Company;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.vacancy.application.dto.VacancyRequest;
import com.comunidev.comunidevbackend.vacancy.application.port.out.VacancyRepositoryPort;
import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VacancyServiceTest {

    private FakeVacancyRepository vacancies;
    private FakeCompanyRepository companies;
    private VacancyService service;
    private String companyId;

    @BeforeEach
    void setUp() {
        vacancies = new FakeVacancyRepository();
        companies = new FakeCompanyRepository();
        service = new VacancyService(vacancies, companies);

        Company company = Company.create("DevSolutions");
        companies.save(company);
        companyId = company.getId();
    }

    @Test
    @DisplayName("createVacancy la registra ACTIVA y recalcula el contador de la empresa")
    void createVacancyRegistraActivaYActualizaContador() {
        Vacancy saved = service.createVacancy(solicitudValida());

        assertEquals(Vacancy.ESTADO_ACTIVA, saved.getEstado());
        assertEquals(companyId, saved.getCompanyId());
        assertEquals(1, companies.contador(companyId));
    }

    @Test
    @DisplayName("createVacancy sin tecnologias se rechaza (CA-HU-40)")
    void createVacancySinTecnologiasRechaza() {
        VacancyRequest request = new VacancyRequest(companyId, "r-1", "Backend", "Desc",
                null, null, List.of(), "MID", "REMOTO", "TIEMPO_COMPLETO",
                null, null, 1, futuro(), null);

        assertThrows(BusinessException.class, () -> service.createVacancy(request));
    }

    @Test
    @DisplayName("createVacancy con fecha limite pasada se rechaza (RN-22)")
    void createVacancyConFechaPasadaRechaza() {
        VacancyRequest request = new VacancyRequest(companyId, "r-1", "Backend", "Desc",
                null, null, List.of("Java"), "MID", "REMOTO", "TIEMPO_COMPLETO",
                null, null, 1, Instant.now().minus(1, ChronoUnit.DAYS).toString(), null);

        BusinessException error = assertThrows(BusinessException.class,
                () -> service.createVacancy(request));
        assertTrue(error.getMessage().contains("fecha límite"));
    }

    @Test
    @DisplayName("createVacancy sin ubicacion y que no es remota se rechaza (CA-HU-40)")
    void createVacancySinUbicacionNoRemotaRechaza() {
        VacancyRequest request = new VacancyRequest(companyId, "r-1", "Backend", "Desc",
                null, null, List.of("Java"), "MID", "PRESENCIAL", "TIEMPO_COMPLETO",
                null, null, 1, futuro(), null);

        assertThrows(BusinessException.class, () -> service.createVacancy(request));
    }

    @Test
    @DisplayName("createVacancy remota no exige ubicacion")
    void createVacancyRemotoNoRequiereUbicacion() {
        Vacancy saved = service.createVacancy(solicitudValida());

        assertEquals(Vacancy.ESTADO_ACTIVA, saved.getEstado());
        assertEquals(Vacancy.MODALIDAD_REMOTO, saved.getModalidad());
    }

    @Test
    @DisplayName("getVacancies sin estado devuelve solo las ACTIVAS (CA-HU-42)")
    void getVacanciesPorDefectoSoloActiva() {
        service.createVacancy(solicitudValida());

        Vacancy cerrada = service.createVacancy(solicitudValida());
        VacancyRequest cierre = new VacancyRequest(null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, Vacancy.ESTADO_CERRADA);
        service.updateVacancy(cerrada.getId(), cierre);

        List<Vacancy> result = service.getVacancies(null);
        assertEquals(1, result.size());
        assertEquals(Vacancy.ESTADO_ACTIVA, result.get(0).getEstado());
    }

    @Test
    @DisplayName("getVacancies deja de mostrar una vacante que acaba de vencer (RN-21)")
    void getVacanciesExcluyeLasQueAcabanDeVencer() {
        Vacancy vencida = Vacancy.create(companyId, "r-1", "Frontend");
        vencida.setDescripcion("Desc");
        vencida.setModalidad(Vacancy.MODALIDAD_REMOTO);
        vencida.setTecnologias(List.of("Angular"));
        vencida.setFechaLimite(Instant.now().minus(1, ChronoUnit.DAYS));
        vacancies.save(vencida);

        List<Vacancy> result = service.getVacancies(null);

        assertTrue(result.isEmpty(), "la vacante vencida no debe aparecer en el listado");
        assertEquals(Vacancy.ESTADO_VENCIDA, vacancies.findById(vencida.getId()).orElseThrow().getEstado());
    }

    @Test
    @DisplayName("getVacanciesByRecruiter devuelve solo las de ese reclutador y en cualquier estado")
    void getVacanciesByRecruiterFiltraPorAutor() {
        service.createVacancy(solicitudValida()); // recruiter-1, ACTIVA

        Vacancy deOtro = Vacancy.create(companyId, "recruiter-2", "Otra vacante");
        vacancies.save(deOtro);

        Vacancy propia = service.createVacancy(solicitudValida()); // recruiter-1
        VacancyRequest cierre = new VacancyRequest(null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, Vacancy.ESTADO_CERRADA);
        service.updateVacancy(propia.getId(), cierre);

        List<Vacancy> result = service.getVacanciesByRecruiter("recruiter-1");

        assertEquals(2, result.size(), "solo las dos que publico recruiter-1");
        assertTrue(result.stream().noneMatch(v -> "recruiter-2".equals(v.getRecruiterId())));
        assertTrue(result.stream().anyMatch(v -> Vacancy.ESTADO_CERRADA.equals(v.getEstado())),
                "el reclutador debe ver tambien sus vacantes cerradas");
    }

    @Test
    @DisplayName("updateVacancy permite cerrar manualmente sin exigir requisitos de activa (HU-41)")
    void updateVacancyPuedeCerrarManualmente() {
        Vacancy created = service.createVacancy(solicitudValida());

        VacancyRequest cierre = new VacancyRequest(null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, Vacancy.ESTADO_CERRADA);
        Vacancy cerrada = service.updateVacancy(created.getId(), cierre);

        assertEquals(Vacancy.ESTADO_CERRADA, cerrada.getEstado());
        assertEquals(0, companies.contador(companyId), "al cerrar deja de contar como activa");
    }

    @Test
    @DisplayName("updateVacancy que vuelve a ACTIVA exige cumplir los requisitos")
    void updateVacancyAActivaExigeRequisitos() {
        Vacancy created = service.createVacancy(solicitudValida());
        VacancyRequest cierre = new VacancyRequest(null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, Vacancy.ESTADO_CERRADA);
        service.updateVacancy(created.getId(), cierre);

        // Sin tecnologias no puede volver a ACTIVA.
        VacancyRequest sinTecnologias = new VacancyRequest(null, null, null, null, null, null,
                List.of(), null, null, null, null, null, null, null, Vacancy.ESTADO_ACTIVA);

        assertThrows(BusinessException.class, () -> service.updateVacancy(created.getId(), sinTecnologias));
    }

    // ---------------------------------------------------------------- helpers

    private VacancyRequest solicitudValida() {
        return new VacancyRequest(companyId, "recruiter-1", "Backend Developer",
                "Buscamos dev con Spring Boot", null, null, List.of("Java", "Spring Boot"),
                "MID", Vacancy.MODALIDAD_REMOTO, "TIEMPO_COMPLETO", null, null, 2,
                futuro(), null);
    }

    private String futuro() {
        return Instant.now().plus(30, ChronoUnit.DAYS).toString();
    }

    // ------------------------------------------------------------------ fakes

    private static class FakeVacancyRepository implements VacancyRepositoryPort {
        private final Map<String, Vacancy> store = new LinkedHashMap<>();
        private int seq;

        @Override
        public Vacancy save(Vacancy vacancy) {
            if (vacancy.getId() == null) vacancy.setId("v-" + (++seq));
            store.put(vacancy.getId(), vacancy);
            return vacancy;
        }

        @Override
        public Optional<Vacancy> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public List<Vacancy> findAllByEstado(String estado) {
            return store.values().stream().filter(v -> estado.equals(v.getEstado())).toList();
        }

        @Override
        public List<Vacancy> findAllByCompanyId(String companyId) {
            return store.values().stream().filter(v -> companyId.equals(v.getCompanyId())).toList();
        }

        @Override
        public List<Vacancy> findAllByRecruiterId(String recruiterId) {
            return store.values().stream().filter(v -> recruiterId.equals(v.getRecruiterId())).toList();
        }

        @Override
        public int countByCompanyIdAndEstado(String companyId, String estado) {
            return (int) store.values().stream()
                    .filter(v -> companyId.equals(v.getCompanyId()) && estado.equals(v.getEstado()))
                    .count();
        }
    }

    private static class FakeCompanyRepository implements CompanyRepositoryPort {
        private final Map<String, Company> store = new LinkedHashMap<>();
        private final Map<String, Integer> contadores = new LinkedHashMap<>();
        private int seq;

        @Override
        public Company save(Company company) {
            if (company.getId() == null) company.setId("c-" + (++seq));
            store.put(company.getId(), company);
            return company;
        }

        @Override
        public Optional<Company> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public List<Company> findAll() {
            return List.copyOf(store.values());
        }

        @Override
        public void setVacantesActivasCount(String companyId, int count) {
            contadores.put(companyId, count);
        }

        int contador(String companyId) {
            return contadores.getOrDefault(companyId, 0);
        }
    }
}
