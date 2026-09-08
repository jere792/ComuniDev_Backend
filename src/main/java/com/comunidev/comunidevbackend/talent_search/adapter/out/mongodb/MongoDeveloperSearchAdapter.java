package com.comunidev.comunidevbackend.talent_search.adapter.out.mongodb;

import com.comunidev.comunidevbackend.talent_search.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.talent_search.application.dto.TalentSearchRequest;
import com.comunidev.comunidevbackend.talent_search.application.port.out.DeveloperSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoDeveloperSearchAdapter implements DeveloperSearchPort {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<DeveloperProfileResponse> searchDevelopers(TalentSearchRequest request) {
        Query query = buildSearchQuery(request);
        query.limit(request.getSize() != null ? request.getSize() : 20);

        if (request.getPage() != null && request.getSize() != null) {
            query.skip((long) request.getPage() * request.getSize());
        }

        return mongoTemplate.find(query, DeveloperProfileResponse.class, "developer_profiles");
    }

    private Query buildSearchQuery(TalentSearchRequest request) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
            criteriaList.add(Criteria.where("tecnologias.nombre").in(request.getTechnologies()));
        }

        if (request.getExperienceLevel() != null && !request.getExperienceLevel().isBlank()) {
            criteriaList.add(Criteria.where("tecnologias.nivel").is(request.getExperienceLevel()));
        }

        if (request.getLocationCountry() != null && !request.getLocationCountry().isBlank()) {
            criteriaList.add(Criteria.where("ubicacion.pais").is(request.getLocationCountry()));
        }

        if (request.getLocationCity() != null && !request.getLocationCity().isBlank()) {
            criteriaList.add(Criteria.where("ubicacion.ciudad").is(request.getLocationCity()));
        }

        if (request.getRemoteAvailable() != null && request.getRemoteAvailable()) {
            criteriaList.add(Criteria.where("disponibilidadLaboral.modalidades").is("REMOTO"));
        }

        if (request.getAvailability() != null && !request.getAvailability().isBlank()) {
            criteriaList.add(Criteria.where("disponibilidadLaboral.buscandoEmpleo").is(true));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return query;
    }
}
