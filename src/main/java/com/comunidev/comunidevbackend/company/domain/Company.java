package com.comunidev.comunidevbackend.company.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Empresa que ofrece oportunidades laborales (doc/03-ENTIDADES.md 1.4).
 *
 * <p>La vacante pertenece a la empresa, no al reclutador: un reclutador
 * "autorizado" solo gestiona las vacantes de la empresa (HU-38/HU-39).
 * Aparece en el mapa laboral cuando tiene al menos una vacante activa (HU-46).
 */
@Getter
@Setter
@Document(collection = "companies")
public class Company extends AggregateRoot<String> {

    /** HU-39: el administrador valida la empresa antes de que publique vacantes. */
    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    public static final String ESTADO_VERIFICADA = "VERIFICADA";
    public static final String ESTADO_RECHAZADA = "RECHAZADA";

    /**
     * Reclutador que creó y administra la empresa. Es lo que permite responder
     * "¿cuál es mi empresa?" en {@code myCompany}; sin esto no hay forma de
     * ligar un reclutador a su organización. Única: la pantalla es "Mi empresa".
     */
    @Indexed(unique = true)
    private String recruiterId;

    private String nombre;
    private String descripcion;
    private String sector;
    private String sitioWeb;
    private String logoUrl;
    private String bannerUrl;

    /** Datos legales de la organización. Antes vivían en recruiter_profiles. */
    private String ruc;
    private String fechaCreacion;

    private Ubicacion ubicacion;
    private Contacto contacto;
    private Redes redes;

    @Indexed
    private String estadoVerificacion = ESTADO_PENDIENTE;

    /** RN-31: se recalcula al publicar, vencer o cerrar una vacante. */
    private Integer vacantesActivasCount = 0;

    private Instant createdAt;
    private Instant updatedAt;

    /**
     * HU-39 exige que el administrador verifique la empresa antes de publicar.
     * Todavía no se valida: no existe el flujo de admin ni JWT (omite auth),
     * así que cualquier empresa puede publicar. {@code estadoVerificacion}
     * queda guardado para cuando ese flujo se implemente.
     */
    public static Company create(String recruiterId, String nombre) {
        Company company = new Company();
        company.setRecruiterId(recruiterId);
        company.setNombre(nombre);
        company.setEstadoVerificacion(ESTADO_PENDIENTE);
        company.setVacantesActivasCount(0);
        Instant now = Instant.now();
        company.setCreatedAt(now);
        company.setUpdatedAt(now);
        return company;
    }

    @Getter
    @Setter
    public static class Ubicacion {
        private String pais;
        private String ciudad;
        private String distrito;
        private String direccion;
    }

    @Getter
    @Setter
    public static class Contacto {
        private String email;
        private String telefono;
    }

    @Getter
    @Setter
    public static class Redes {
        private String linkedin;
        private String github;
    }
}
