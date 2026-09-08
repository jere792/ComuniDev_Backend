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
    private String fotoPerfilUrl;
    private String tituloProfesional;
    private String bio;
    private String ubicacionPais;
    private String ubicacionCiudad;
    private List<TechnologyDto> tecnologias;
    private List<String> habilidadesBlandas;
    private DisponibilidadDto disponibilidadLaboral;
    private Boolean remoto;
    private List<ExperienciaDto> experiencias;

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
    public static class DisponibilidadDto {
        private Boolean buscandoEmpleo;
        private List<String> modalidades;
        private List<String> tiposContrato;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExperienciaDto {
        private String empresa;
        private String puesto;
        private String descripcion;
        private String fechaInicio;
        private String fechaFin;
        private Boolean actual;
    }
}
