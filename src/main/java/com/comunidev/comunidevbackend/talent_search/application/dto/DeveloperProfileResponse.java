package com.comunidev.comunidevbackend.talent_search.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperProfileResponse {
    private String id;
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String telefono;
    private String fotoPerfilUrl;
    private String bannerUrl;
    private String bio;
    private String sitioWeb;
    private String ubicacionPais;
    private String ubicacionDepartamento;
    private String ubicacionCiudad;
    private String estadoCuenta;
    private String createdAt;
    private Integer seguidoresCount;
    private Integer siguiendoCount;
    private Integer conexionesCount;
    private String tituloProfesional;
    private List<TechnologyDto> tecnologias;
    private List<String> habilidadesBlandas;
    private List<ExperienciaDto> experiencias;
    private List<EducacionDto> educacion;
    private List<CertificadoDto> certificados;
    private List<ProyectoDto> proyectos;
    private CvDto cv;
    private EnlacesDto enlaces;
    private DisponibilidadDto disponibilidadLaboral;
    private Boolean remoto;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyDto {
        private String nombre;
        private String nivel;
        private Integer aniosExperiencia;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExperienciaDto {
        private String empresa;
        private String cargo;
        private String descripcion;
        private String fechaInicio;
        private String fechaFin;
        private Boolean actual;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EducacionDto {
        private String institucion;
        private String titulo;
        private String fechaInicio;
        private String fechaFin;
        private Boolean actual;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificadoDto {
        private String nombre;
        private String institucion;
        private String fechaObtencion;
        private String url;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProyectoDto {
        private String nombre;
        private String descripcion;
        private String url;
        private List<String> tecnologias;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CvDto {
        private String archivoUrl;
        private String nombreArchivo;
        private String actualizadoEn;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnlacesDto {
        private String github;
        private String linkedin;
        private String portafolio;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisponibilidadDto {
        private Boolean buscandoEmpleo;
        private List<String> modalidades;
        private List<String> tiposContrato;
        private PretensionSalarialDto pretensionSalarial;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PretensionSalarialDto {
        private String moneda;
        private Integer minimo;
        private Integer maximo;
    }
}
