package com.comunidev.comunidevbackend.recruiter_profile.adapter.in.graphql;

import com.comunidev.comunidevbackend.recruiter_profile.application.port.out.RecruiterProfileRepositoryPort;
import com.comunidev.comunidevbackend.recruiter_profile.adapter.out.mongodb.MongoRecruiterProfileRepository;
import com.comunidev.comunidevbackend.recruiter_profile.domain.RecruiterProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RecruiterProfileGraphQLResolver {

    private final RecruiterProfileRepositoryPort recruiterProfileRepositoryPort;
    private final MongoRecruiterProfileRepository mongoRepository;

    @QueryMapping
    public RecruiterProfile recruiterProfile(@Argument String userId) {
        return recruiterProfileRepositoryPort.findByUserId(userId).orElse(null);
    }

    @MutationMapping
    public RecruiterProfile createRecruiterProfile(
            @Argument String userId,
            @Argument String cargo,
            @Argument String ruc,
            @Argument String lema,
            @Argument Integer anioCreacion,
            @Argument String modalidadTrabajo,
            @Argument RecruiterProfile.RedesSociales redesSociales) {

        RecruiterProfile profile = RecruiterProfile.create(userId);
        profile.setCargo(cargo);
        profile.setRuc(ruc);
        profile.setLema(lema);
        profile.setAnioCreacion(anioCreacion);
        profile.setModalidadTrabajo(modalidadTrabajo);
        profile.setRedesSociales(redesSociales);

        return recruiterProfileRepositoryPort.save(profile);
    }

    @MutationMapping
    public RecruiterProfile updateRecruiterProfile(
            @Argument String id,
            @Argument String cargo,
            @Argument String ruc,
            @Argument String lema,
            @Argument Integer anioCreacion,
            @Argument String modalidadTrabajo,
            @Argument RecruiterProfile.RedesSociales redesSociales) {

        return mongoRepository.findById(id)
                .map(profile -> {
                    if (cargo != null) profile.setCargo(cargo);
                    if (ruc != null) profile.setRuc(ruc);
                    if (lema != null) profile.setLema(lema);
                    if (anioCreacion != null) profile.setAnioCreacion(anioCreacion);
                    if (modalidadTrabajo != null) profile.setModalidadTrabajo(modalidadTrabajo);
                    if (redesSociales != null) profile.setRedesSociales(redesSociales);
                    profile.setUpdatedAt(java.time.Instant.now());
                    return recruiterProfileRepositoryPort.save(profile);
                })
                .orElse(null);
    }
}
