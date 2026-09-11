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
            @Argument String nombres,
            @Argument String apellidos,
            @Argument String bio,
            @Argument String cargo,
            @Argument String telefono,
            @Argument String linkedinUrl) {

        RecruiterProfile profile = RecruiterProfile.create(userId);
        profile.setNombres(nombres);
        profile.setApellidos(apellidos);
        profile.setBio(bio);
        profile.setCargo(cargo);
        profile.setTelefono(telefono);
        profile.setLinkedinUrl(linkedinUrl);

        return recruiterProfileRepositoryPort.save(profile);
    }

    @MutationMapping
    public RecruiterProfile updateRecruiterProfile(
            @Argument String id,
            @Argument String nombres,
            @Argument String apellidos,
            @Argument String bio,
            @Argument String cargo,
            @Argument String bannerUrl,
            @Argument String telefono,
            @Argument String linkedinUrl) {

        return mongoRepository.findById(id)
                .map(profile -> {
                    if (nombres != null) profile.setNombres(nombres);
                    if (apellidos != null) profile.setApellidos(apellidos);
                    if (bio != null) profile.setBio(bio);
                    if (cargo != null) profile.setCargo(cargo);
                    if (bannerUrl != null) profile.setBannerUrl(bannerUrl);
                    if (telefono != null) profile.setTelefono(telefono);
                    if (linkedinUrl != null) profile.setLinkedinUrl(linkedinUrl);
                    profile.setUpdatedAt(java.time.Instant.now());
                    return recruiterProfileRepositoryPort.save(profile);
                })
                .orElse(null);
    }
}
