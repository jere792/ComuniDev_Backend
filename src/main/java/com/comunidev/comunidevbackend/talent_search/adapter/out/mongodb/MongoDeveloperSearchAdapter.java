package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.port.out.DeveloperSearchPort;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoDeveloperSearchAdapter implements DeveloperSearchPort {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<DeveloperProfileResponse> searchDevelopers(TalentSearchRequest request) {
        List<org.springframework.data.mongodb.core.aggregation.AggregationOperation> operations = new ArrayList<>();

        operations.add(Aggregation.lookup("users", "userId", "_id", "user_data"));

        List<Criteria> criteriaList = new ArrayList<>();

        if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
            criteriaList.add(Criteria.where("tecnologias.nombre").in(request.getTechnologies()));
        }

        if (request.getExperienceLevel() != null && !request.getExperienceLevel().isBlank()) {
            criteriaList.add(Criteria.where("tecnologias.nivel").is(request.getExperienceLevel()));
        }

        if (request.getLocationCountry() != null && !request.getLocationCountry().isBlank()) {
            criteriaList.add(Criteria.where("user_data.ubicacion.pais").is(request.getLocationCountry()));
        }

        if (request.getLocationCity() != null && !request.getLocationCity().isBlank()) {
            criteriaList.add(Criteria.where("user_data.ubicacion.ciudad").is(request.getLocationCity()));
        }

        if (request.getRemoteAvailable() != null && request.getRemoteAvailable()) {
            criteriaList.add(Criteria.where("disponibilidadLaboral.modalidades").is("REMOTO"));
        }

        if (request.getAvailability() != null && !request.getAvailability().isBlank()) {
            criteriaList.add(Criteria.where("disponibilidadLaboral.buscandoEmpleo").is(true));
        }

        if (!criteriaList.isEmpty()) {
            operations.add(Aggregation.match(new Criteria().andOperator(criteriaList.toArray(new Criteria[0]))));
        }

        int limit = request.getSize() != null ? request.getSize() : 20;
        operations.add(Aggregation.limit(limit));

        if (request.getPage() != null && request.getSize() != null) {
            operations.add(Aggregation.skip((long) request.getPage() * request.getSize()));
        }

        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "developer_profiles", Document.class);

        return results.getMappedResults().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DeveloperProfileResponse mapToResponse(Document doc) {
        DeveloperProfileResponse response = new DeveloperProfileResponse();
        response.setTituloProfesional(doc.getString("tituloProfesional"));

        @SuppressWarnings("unchecked")
        List<Document> user_data = (List<Document>) doc.get("user_data");
        if (user_data != null && !user_data.isEmpty()) {
            Document user = user_data.get(0);
            response.setNombre(user.getString("nombre"));
            response.setNombreUsuario(user.getString("nombreUsuario"));
            response.setFotoPerfilUrl(user.getString("fotoPerfilUrl"));
            response.setBio(user.getString("bio"));
            @SuppressWarnings("unchecked")
            Document ubicacion = (Document) user.get("ubicacion");
            if (ubicacion != null) {
                response.setUbicacionPais(ubicacion.getString("pais"));
                response.setUbicacionCiudad(ubicacion.getString("ciudad"));
            }
        }

        return response;
    }
}
