package com.comunidev.comunidevbackend.developer_profile.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "developer_profiles")
public class DeveloperProfile extends AggregateRoot<String> {
    private String userId;
    private String tituloProfesional;
    private String bannerUrl;
    private String bio;
    private Ubicacion ubicacion;
    private List<Tecnologia> tecnologias = new ArrayList<>();
    private List<String> habilidadesBlandas = new ArrayList<>();
    private List<Experiencia> experiencias = new ArrayList<>();
    private List<Educacion> educacion = new ArrayList<>();
    private List<Certificado> certificados = new ArrayList<>();
    private List<Proyecto> proyectos = new ArrayList<>();
    private CV cv;
    private DisponibilidadLaboral disponibilidadLaboral;
    private Enlaces enlaces;
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    public static class Ubicacion {
        private String pais;
        private String ciudad;
        private String distrito;
    }

    @Getter
    @Setter
    public static class Tecnologia {
        private String nombre;
        private String nivel;
        private Integer aniosExperiencia;
    }

    @Getter
    @Setter
    public static class Experiencia {
        private String empresa;
        private String puesto;
        private String descripcion;
        private String fechaInicio;
        private String fechaFin;
        private Boolean actual;
    }

    @Getter
    @Setter
    public static class Educacion {
        private String institucion;
        private String grado;
        private String fechaInicio;
        private String fechaFin;
        private Boolean actual;
    }

    @Getter
    @Setter
    public static class Certificado {
        private String titulo;
        private String institucion;
        private String fechaObtencion;
        private String credencialUrl;
        private String archivoUrl;
    }

    @Getter
    @Setter
    public static class Proyecto {
        private String titulo;
        private String descripcion;
        private List<String> tecnologias = new ArrayList<>();
        private String repositorioUrl;
        private String demoUrl;
        private List<String> imagenes = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class CV {
        private String archivoUrl;
        private String nombreArchivo;
        private Instant actualizadoEn;
        private String visiblePara;
    }

    @Getter
    @Setter
    public static class DisponibilidadLaboral {
        private Boolean buscandoEmpleo = false;
        private List<String> modalidades = new ArrayList<>();
        private List<String> tiposContrato = new ArrayList<>();
        private PretensionSalarial pretensionSalarial;
    }

    @Getter
    @Setter
    public static class PretensionSalarial {
        private String moneda;
        private Integer minimo;
        private Integer maximo;
    }

    @Getter
    @Setter
    public static class Enlaces {
        private String github;
        private String linkedin;
        private String portafolio;
    }

    public static DeveloperProfile create(String userId) {
        DeveloperProfile profile = new DeveloperProfile();
        profile.setUserId(userId);
        profile.setCreatedAt(Instant.now());
        profile.setUpdatedAt(Instant.now());
        return profile;
    }
}
