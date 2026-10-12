package com.comunidev.comunidevbackend.vacancy.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Oferta laboral publicada por una empresa (doc/03-ENTIDADES.md 6.1).
 *
 * <p>Estados (HU-41):
 * <ul>
 *   <li>{@code VENCIDA} — la pone el <b>sistema</b> al pasar la fecha límite.</li>
 *   <li>{@code CERRADA} — la pone el <b>reclutador</b> manualmente.</li>
 * </ul>
 */
@Getter
@Setter
@Document(collection = "vacancies")
// VacantesByCompany(vacancies del reclutador) y el listado público por estado.
@CompoundIndex(name = "idx_vacancies_company_estado", def = "{'companyId': 1, 'estado': 1}")
// Filtros de HU-42: modalidad + nivel + estado.
@CompoundIndex(name = "idx_vacancies_modalidad_nivel_estado",
        def = "{'modalidad': 1, 'nivelExperiencia': 1, 'estado': 1}")
// Filtro de HU-42 por tecnología (multikey sobre el array).
@CompoundIndex(name = "idx_vacancies_tecnologias", def = "{'tecnologias': 1}")
public class Vacancy extends AggregateRoot<String> {

    public static final String ESTADO_ACTIVA = "ACTIVA";
    public static final String ESTADO_PAUSADA = "PAUSADA";
    public static final String ESTADO_CERRADA = "CERRADA";
    public static final String ESTADO_VENCIDA = "VENCIDA";
    public static final String ESTADO_BORRADOR = "BORRADOR";
    public static final String ESTADO_PENDIENTE_REVISION = "PENDIENTE_REVISION";
    public static final String ESTADO_RECHAZADA = "RECHAZADA";

    /** HU-42: el listado público solo debe mostrar vacantes vigentes (CA-HU-42). */
    public static final String ESTADO_POR_DEFECTO = ESTADO_ACTIVA;

    public static final String MODALIDAD_REMOTO = "REMOTO";

    private String companyId;
    /** "Mis Ofertas" (HU-44) filtra por quién publicó, no por empresa. */
    @Indexed
    private String recruiterId;
    private String titulo;
    private String descripcion;
    private List<String> responsabilidades = new ArrayList<>();
    private List<String> requisitos = new ArrayList<>();
    private List<String> tecnologias = new ArrayList<>();
    private String nivelExperiencia;
    private String modalidad;
    private String tipoContrato;
    /** Texto libre (ej. "Lima, Perú" o "Remoto"). El mapa con GeoJSON es HU-46. */
    private String ubicacion;
    private Remuneracion remuneracion;
    private Integer vacantesDisponibles;
    private String estado;
    /** RN-21: al vencer pasa a VENCIDA y deja de recibir postulaciones. */
    @Indexed
    private Instant fechaLimite;
    private Boolean destacada = false;
    private Integer postulacionesCount = 0;
    private Instant createdAt;
    private Instant updatedAt;

    /** true si la fecha límite ya pasó. */
    public boolean estaVencida(Instant ahora) {
        return fechaLimite != null && fechaLimite.isBefore(ahora);
    }

    /**
     * RN-21/HU-41: solo una vacante ACTIVA vencida pasa a VENCIDA.
     *
     * @return true si cambió de estado (para que el servicio recalcule el contador).
     */
    public boolean marcarVencidaSiAplica(Instant ahora) {
        if (ESTADO_ACTIVA.equals(estado) && estaVencida(ahora)) {
            estado = ESTADO_VENCIDA;
            updatedAt = Instant.now();
            return true;
        }
        return false;
    }

    public static Vacancy create(String companyId, String recruiterId, String titulo) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCompanyId(companyId);
        vacancy.setRecruiterId(recruiterId);
        vacancy.setTitulo(titulo);
        vacancy.setEstado(ESTADO_ACTIVA);
        vacancy.setDestacada(false);
        vacancy.setPostulacionesCount(0);
        Instant now = Instant.now();
        vacancy.setCreatedAt(now);
        vacancy.setUpdatedAt(now);
        return vacancy;
    }

    @Getter
    @Setter
    public static class Remuneracion {
        private Boolean visible;
        private String moneda;
        private Double minimo;
        private Double maximo;
        private String periodo;
    }
}
