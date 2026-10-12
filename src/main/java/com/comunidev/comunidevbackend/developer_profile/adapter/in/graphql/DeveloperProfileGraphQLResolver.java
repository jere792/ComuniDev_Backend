package com.comunidev.comunidevbackend.developer_profile.adapter.in.graphql;

import com.comunidev.comunidevbackend.developer_profile.application.dto.DeveloperProfileResponse;
import com.comunidev.comunidevbackend.developer_profile.application.port.out.DeveloperProfileRepositoryPort;
import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeveloperProfileGraphQLResolver {

    private final DeveloperProfileRepositoryPort developerProfileRepositoryPort;

    /**
     * Nombre real que consume el frontend. Se mantiene tambien
     * {@code developerProfile} como alias para no romper queries antiguas.
     */
    @QueryMapping
    public DeveloperProfileResponse developerProfileByUserId(@Argument String userId) {
        return DeveloperProfileResponse.from(
                developerProfileRepositoryPort.findByUserId(userId).orElse(null));
    }

    @QueryMapping
    public DeveloperProfileResponse developerProfile(@Argument String userId) {
        return DeveloperProfileResponse.from(
                developerProfileRepositoryPort.findByUserId(userId).orElse(null));
    }

    @MutationMapping
    public DeveloperProfileResponse createDeveloperProfile(
            @Argument String userId,
            @Argument String tituloProfesional,
            @Argument List<DeveloperProfile.Tecnologia> tecnologias,
            @Argument List<String> habilidadesBlandas,
            @Argument DeveloperProfile.DisponibilidadLaboral disponibilidadLaboral,
            @Argument DeveloperProfile.Enlaces enlaces) {

        DeveloperProfile profile = developerProfileRepositoryPort.findByUserId(userId)
                .orElseGet(() -> DeveloperProfile.create(userId));

        apply(profile, tituloProfesional, tecnologias, habilidadesBlandas,
                disponibilidadLaboral, enlaces);
        return DeveloperProfileResponse.from(developerProfileRepositoryPort.save(profile));
    }

    /**
     * Se identifica por el id del perfil, pero tambien acepta que venga el
     * {@code userId} para no romper el frontend actual.
     */
    @MutationMapping
    public DeveloperProfileResponse updateDeveloperProfile(
            @Argument String id,
            @Argument String tituloProfesional,
            @Argument List<DeveloperProfile.Tecnologia> tecnologias,
            @Argument List<String> habilidadesBlandas,
            @Argument DeveloperProfile.DisponibilidadLaboral disponibilidadLaboral,
            @Argument DeveloperProfile.Enlaces enlaces) {

        DeveloperProfile profile = developerProfileRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeveloperProfile", "id", id));

        apply(profile, tituloProfesional, tecnologias, habilidadesBlandas,
                disponibilidadLaboral, enlaces);
        return DeveloperProfileResponse.from(developerProfileRepositoryPort.save(profile));
    }

    /** Aplica solo los campos enviados, para que un update parcial no borre datos. */
    private void apply(DeveloperProfile profile,
                       String tituloProfesional,
                       List<DeveloperProfile.Tecnologia> tecnologias,
                       List<String> habilidadesBlandas,
                       DeveloperProfile.DisponibilidadLaboral disponibilidadLaboral,
                       DeveloperProfile.Enlaces enlaces) {

        if (tituloProfesional != null) profile.setTituloProfesional(tituloProfesional);
        if (tecnologias != null) profile.setTecnologias(new ArrayList<>(tecnologias));
        if (habilidadesBlandas != null) profile.setHabilidadesBlandas(new ArrayList<>(habilidadesBlandas));
        if (disponibilidadLaboral != null) profile.setDisponibilidadLaboral(disponibilidadLaboral);
        if (enlaces != null) profile.setEnlaces(enlaces);
        profile.setUpdatedAt(Instant.now());
    }
}
