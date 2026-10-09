package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.port.out.DeveloperSearchPort;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoDeveloperSearchAdapter implements DeveloperSearchPort {

    private static final List<String> DEVELOPER_ROLES = List.of("DEVELOPER", "DESARROLLADOR");

    private final MongoTemplate mongoTemplate;

    @Override
    public List<DeveloperProfileResponse> searchDevelopers(TalentSearchRequest request) {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(profileLookup());

        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("roles").in(DEVELOPER_ROLES));
        criteriaList.add(Criteria.where("baneo.baneado").ne(true));

        if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
            criteriaList.add(Criteria.where("profile_data.tecnologias.nombre").in(request.getTechnologies()));
        }

        if (request.getExperienceLevel() != null && !request.getExperienceLevel().isBlank()) {
            criteriaList.add(Criteria.where("profile_data.tecnologias.nivel").is(request.getExperienceLevel()));
        }

        if (request.getLocationCountry() != null && !request.getLocationCountry().isBlank()) {
            criteriaList.add(Criteria.where("ubicacion.pais").is(request.getLocationCountry()));
        }

        if (request.getLocationCity() != null && !request.getLocationCity().isBlank()) {
            criteriaList.add(Criteria.where("ubicacion.ciudad").is(request.getLocationCity()));
        }

        if (request.getRemoteAvailable() != null && request.getRemoteAvailable()) {
            criteriaList.add(Criteria.where("profile_data.disponibilidadLaboral.modalidades").is("REMOTO"));
        }

        if (request.getAvailability() != null && !request.getAvailability().isBlank()) {
            criteriaList.add(Criteria.where("profile_data.disponibilidadLaboral.buscandoEmpleo").is(true));
        }

        operations.add(Aggregation.match(new Criteria().andOperator(criteriaList.toArray(new Criteria[0]))));

        int size = request.getSize() != null ? request.getSize() : 20;
        int page = request.getPage() != null ? request.getPage() : 0;
        if (page > 0) {
            operations.add(Aggregation.skip((long) page * size));
        }
        operations.add(Aggregation.limit(size));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "users", Document.class);

        return results.getMappedResults().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AggregationOperation profileLookup() {
        return context -> new Document("$lookup",
                new Document("from", "developer_profiles")
                        .append("let", new Document("uid", new Document("$toString", "$_id")))
                        .append("pipeline", List.of(
                                new Document("$match", new Document("$expr",
                                        new Document("$eq", List.of(
                                                new Document("$toString", "$userId"),
                                                "$$uid"))))))
                        .append("as", "profile_data"));
    }

    private DeveloperProfileResponse mapToResponse(Document userDoc) {
        DeveloperProfileResponse response = new DeveloperProfileResponse();

        Object id = userDoc.get("_id");
        if (id != null) {
            response.setId(id.toString());
        }
        response.setNombre(userDoc.getString("nombre"));
        response.setNombreUsuario(userDoc.getString("nombreUsuario"));
        response.setEmail(userDoc.getString("email"));
        response.setTelefono(userDoc.getString("telefono"));
        response.setFotoPerfilUrl(userDoc.getString("fotoPerfilUrl"));
        response.setBannerUrl(userDoc.getString("bannerUrl"));
        response.setBio(userDoc.getString("bio"));
        response.setSitioWeb(userDoc.getString("sitioWeb"));
        response.setEstadoCuenta(userDoc.getString("estadoCuenta"));
        response.setSeguidoresCount(userDoc.getInteger("seguidoresCount"));
        response.setSiguiendoCount(userDoc.getInteger("siguiendoCount"));
        response.setConexionesCount(userDoc.getInteger("conexionesCount"));
        Object createdAt = userDoc.get("createdAt");
        if (createdAt != null) {
            response.setCreatedAt(createdAt.toString());
        }

        Document ubicacion = (Document) userDoc.get("ubicacion");
        if (ubicacion != null) {
            response.setUbicacionPais(ubicacion.getString("pais"));
            response.setUbicacionDepartamento(ubicacion.getString("departamento"));
            response.setUbicacionCiudad(ubicacion.getString("ciudad"));
        }

        Document profile = firstProfile(userDoc);
        if (profile != null) {
            response.setTituloProfesional(profile.getString("tituloProfesional"));
            response.setHabilidadesBlandas(toStringList(profile.get("habilidadesBlandas")));
            response.setTecnologias(mapTechnologies(profile));
            response.setExperiencias(mapExperiences(profile));
            response.setEducacion(mapEducacion(profile));
            response.setCertificados(mapCertificados(profile));
            response.setProyectos(mapProyectos(profile));
            response.setCv(mapCv(profile));
            response.setEnlaces(mapEnlaces(profile));
            DeveloperProfileResponse.DisponibilidadDto disponibilidad = mapDisponibilidad(profile);
            response.setDisponibilidadLaboral(disponibilidad);
            if (disponibilidad != null && disponibilidad.getModalidades() != null) {
                response.setRemoto(disponibilidad.getModalidades().contains("REMOTO"));
            }
        }

        return response;
    }

    private Document firstProfile(Document userDoc) {
        Object raw = userDoc.get("profile_data");
        if (raw instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Document profile) {
            return profile;
        }
        return null;
    }

    private List<String> toStringList(Object raw) {
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        for (Object item : list) {
            if (item != null) {
                result.add(item.toString());
            }
        }
        return result;
    }

    private List<DeveloperProfileResponse.TechnologyDto> mapTechnologies(Document profile) {
        Object raw = profile.get("tecnologias");
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        List<DeveloperProfileResponse.TechnologyDto> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Document tech) {
                result.add(new DeveloperProfileResponse.TechnologyDto(
                        tech.getString("nombre"),
                        tech.getString("nivel"),
                        tech.getInteger("aniosExperiencia")));
            }
        }
        return result;
    }

    private List<DeveloperProfileResponse.ExperienciaDto> mapExperiences(Document profile) {
        Object raw = profile.get("experiencias");
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        List<DeveloperProfileResponse.ExperienciaDto> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Document exp) {
                result.add(new DeveloperProfileResponse.ExperienciaDto(
                        exp.getString("empresa"),
                        exp.getString("puesto"),
                        exp.getString("descripcion"),
                        exp.getString("fechaInicio"),
                        exp.getString("fechaFin"),
                        exp.getBoolean("actual")));
            }
        }
        return result;
    }

    private DeveloperProfileResponse.DisponibilidadDto mapDisponibilidad(Document profile) {
        Object raw = profile.get("disponibilidadLaboral");
        if (!(raw instanceof Document disp)) {
            return null;
        }
        DeveloperProfileResponse.PretensionSalarialDto pretension = null;
        Object rawPretension = disp.get("pretensionSalarial");
        if (rawPretension instanceof Document pret) {
            pretension = new DeveloperProfileResponse.PretensionSalarialDto(
                    pret.getString("moneda"),
                    pret.getInteger("minimo"),
                    pret.getInteger("maximo"));
        }
        return new DeveloperProfileResponse.DisponibilidadDto(
                disp.getBoolean("buscandoEmpleo"),
                toStringList(disp.get("modalidades")),
                toStringList(disp.get("tiposContrato")),
                pretension);
    }

    private List<DeveloperProfileResponse.EducacionDto> mapEducacion(Document profile) {
        Object raw = profile.get("educacion");
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        List<DeveloperProfileResponse.EducacionDto> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Document edu) {
                result.add(new DeveloperProfileResponse.EducacionDto(
                        edu.getString("institucion"),
                        edu.getString("grado"),
                        edu.getString("fechaInicio"),
                        edu.getString("fechaFin"),
                        edu.getBoolean("actual")));
            }
        }
        return result;
    }

    private List<DeveloperProfileResponse.CertificadoDto> mapCertificados(Document profile) {
        Object raw = profile.get("certificados");
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        List<DeveloperProfileResponse.CertificadoDto> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Document cert) {
                result.add(new DeveloperProfileResponse.CertificadoDto(
                        cert.getString("titulo"),
                        cert.getString("institucion"),
                        cert.getString("fechaObtencion"),
                        cert.getString("credencialUrl")));
            }
        }
        return result;
    }

    private List<DeveloperProfileResponse.ProyectoDto> mapProyectos(Document profile) {
        Object raw = profile.get("proyectos");
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        List<DeveloperProfileResponse.ProyectoDto> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Document proj) {
                result.add(new DeveloperProfileResponse.ProyectoDto(
                        proj.getString("titulo"),
                        proj.getString("descripcion"),
                        proj.getString("repositorioUrl"),
                        toStringList(proj.get("tecnologias"))));
            }
        }
        return result;
    }

    private DeveloperProfileResponse.CvDto mapCv(Document profile) {
        Object raw = profile.get("cv");
        if (!(raw instanceof Document cv)) {
            return null;
        }
        Object actualizadoEn = cv.get("actualizadoEn");
        return new DeveloperProfileResponse.CvDto(
                cv.getString("archivoUrl"),
                cv.getString("nombreArchivo"),
                actualizadoEn != null ? actualizadoEn.toString() : null);
    }

    private DeveloperProfileResponse.EnlacesDto mapEnlaces(Document profile) {
        Object raw = profile.get("enlaces");
        if (!(raw instanceof Document enlaces)) {
            return null;
        }
        return new DeveloperProfileResponse.EnlacesDto(
                enlaces.getString("github"),
                enlaces.getString("linkedin"),
                enlaces.getString("portafolio"));
    }
}
