package com.comunidev.comunidevbackend.developer_profile.application.dto;

import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;

import java.util.List;

/**
 * Contrato GraphQL de salida para {@code DeveloperProfile}.
 *
 * El modelo de dominio interno usa nombres mas ricos ({@code puesto},
 * {@code grado}, {@code credencialUrl}, {@code repositorioUrl}/{@code demoUrl}),
 * pero el frontend ya consume otros ({@code cargo}, {@code titulo},
 * {@code nombre}, {@code url}). Este DTO traduce del modelo interno al
 * contrato publico para no romper a los consumidores existentes.
 */
public record DeveloperProfileResponse(
        String id,
        String userId,
        String tituloProfesional,
        List<DeveloperProfile.Tecnologia> tecnologias,
        List<String> habilidadesBlandas,
        List<Experiencia> experiencias,
        List<Educacion> educacion,
        List<Certificado> certificados,
        List<Proyecto> proyectos,
        DeveloperProfile.CV cv,
        DeveloperProfile.DisponibilidadLaboral disponibilidadLaboral,
        DeveloperProfile.Enlaces enlaces) {

    public record Experiencia(String empresa, String cargo, String descripcion,
                              String fechaInicio, String fechaFin, Boolean actual) {
    }

    public record Educacion(String institucion, String titulo, String fechaInicio,
                            String fechaFin, Boolean actual) {
    }

    public record Certificado(String nombre, String institucion, String fechaObtencion,
                              String url, String archivoUrl) {
    }

    public record Proyecto(String nombre, String descripcion, String url,
                           List<String> tecnologias, List<String> imagenes) {
    }

    public static DeveloperProfileResponse from(DeveloperProfile profile) {
        if (profile == null) {
            return null;
        }
        return new DeveloperProfileResponse(
                profile.getId(),
                profile.getUserId(),
                profile.getTituloProfesional(),
                profile.getTecnologias(),
                profile.getHabilidadesBlandas(),
                experiencias(profile.getExperiencias()),
                educacion(profile.getEducacion()),
                certificados(profile.getCertificados()),
                proyectos(profile.getProyectos()),
                profile.getCv(),
                profile.getDisponibilidadLaboral(),
                profile.getEnlaces());
    }

    public static List<DeveloperProfileResponse> fromList(List<DeveloperProfile> profiles) {
        if (profiles == null) {
            return List.of();
        }
        return profiles.stream().map(DeveloperProfileResponse::from).toList();
    }

    private static List<Experiencia> experiencias(List<DeveloperProfile.Experiencia> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(e -> new Experiencia(e.getEmpresa(), e.getPuesto(), e.getDescripcion(),
                        e.getFechaInicio(), e.getFechaFin(), e.getActual()))
                .toList();
    }

    private static List<Educacion> educacion(List<DeveloperProfile.Educacion> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(e -> new Educacion(e.getInstitucion(), e.getGrado(), e.getFechaInicio(),
                        e.getFechaFin(), e.getActual()))
                .toList();
    }

    private static List<Certificado> certificados(List<DeveloperProfile.Certificado> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(c -> new Certificado(c.getTitulo(), c.getInstitucion(), c.getFechaObtencion(),
                        c.getCredencialUrl(), c.getArchivoUrl()))
                .toList();
    }

    private static List<Proyecto> proyectos(List<DeveloperProfile.Proyecto> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(p -> new Proyecto(p.getTitulo(), p.getDescripcion(), urlDe(p),
                        p.getTecnologias(), p.getImagenes()))
                .toList();
    }

    /**
     * El frontend expone un unico enlace por proyecto: prefiere la demo en vivo
     * y si no existe usa el repositorio.
     */
    private static String urlDe(DeveloperProfile.Proyecto proyecto) {
        String demo = proyecto.getDemoUrl();
        return demo != null && !demo.isBlank() ? demo : proyecto.getRepositorioUrl();
    }
}
