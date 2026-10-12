package com.comunidev.comunidevbackend.company.application.service;

import com.comunidev.comunidevbackend.company.application.dto.CompanyRequest;
import com.comunidev.comunidevbackend.company.application.port.out.CompanyRepositoryPort;
import com.comunidev.comunidevbackend.company.domain.Company;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompanyServiceTest {

    private FakeCompanyRepository repository;
    private CompanyService service;

    @BeforeEach
    void setUp() {
        repository = new FakeCompanyRepository();
        service = new CompanyService(repository);
    }

    @Test
    @DisplayName("createCompany deja la empresa PENDIENTE y sin vacantes activas")
    void createCompanyAplicaDefaults() {
        Company saved = service.createCompany(solicitudValida());

        assertEquals("DevSolutions Perú", saved.getNombre());
        assertEquals(Company.ESTADO_PENDIENTE, saved.getEstadoVerificacion());
        assertEquals(0, saved.getVacantesActivasCount());
    }

    @Test
    @DisplayName("createCompany sin nombre se rechaza")
    void createCompanySinNombreRechaza() {
        CompanyRequest request = new CompanyRequest(null, null, null, null, null, null,
                ubicacion(), contacto(), null);

        assertThrows(BusinessException.class, () -> service.createCompany(request));
    }

    @Test
    @DisplayName("createCompany sin correo de contacto se rechaza (03-ENTIDADES.md 1.4)")
    void createCompanySinContactoRechaza() {
        CompanyRequest request = new CompanyRequest("DevSolutions", null, null, null, null, null,
                ubicacion(), null, null);

        assertThrows(BusinessException.class, () -> service.createCompany(request));
    }

    @Test
    @DisplayName("createCompany sin ubicacion se rechaza (03-ENTIDADES.md 1.4)")
    void createCompanySinUbicacionRechaza() {
        CompanyRequest request = new CompanyRequest("DevSolutions", null, null, null, null, null,
                null, contacto(), null);

        assertThrows(BusinessException.class, () -> service.createCompany(request));
    }

    @Test
    @DisplayName("updateCompany no toca los campos que llegan en null")
    void updateCompanyNoTocaCamposNulos() {
        Company created = service.createCompany(solicitudValida());

        CompanyRequest parcial = new CompanyRequest(null, "Empresa de software", null, null,
                null, null, null, null, null);
        Company updated = service.updateCompany(created.getId(), parcial);

        assertEquals("DevSolutions Perú", updated.getNombre(), "el nombre no debia cambiar");
        assertEquals("Empresa de software", updated.getDescripcion());
    }

    @Test
    @DisplayName("getCompany lanza ResourceNotFoundException si no existe")
    void getCompanyInexistenteLanzaError() {
        assertThrows(ResourceNotFoundException.class, () -> service.getCompany("no-existe"));
    }

    // ---------------------------------------------------------------- helpers

    private CompanyRequest solicitudValida() {
        return new CompanyRequest("DevSolutions Perú", "Desarrollo de software",
                "Tecnología", "https://devsolutions.example", null, null,
                ubicacion(), contacto(), null);
    }

    private Company.Ubicacion ubicacion() {
        Company.Ubicacion ubicacion = new Company.Ubicacion();
        ubicacion.setPais("Perú");
        ubicacion.setCiudad("Lima");
        return ubicacion;
    }

    private Company.Contacto contacto() {
        Company.Contacto contacto = new Company.Contacto();
        contacto.setEmail("rrhh@devsolutions.example");
        return contacto;
    }

    // ------------------------------------------------------------------ fakes

    private static class FakeCompanyRepository implements CompanyRepositoryPort {
        private final Map<String, Company> store = new LinkedHashMap<>();
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
            store.get(companyId).setVacantesActivasCount(count);
        }
    }
}
