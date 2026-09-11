package com.comunidev.comunidevbackend.recruiter_profile.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "recruiter_profiles")
public class RecruiterProfile extends AggregateRoot<String> {
    private String userId;
    private String nombres;
    private String apellidos;
    private String bio;
    private String cargo;
    private String bannerUrl;
    private String telefono;
    private String linkedinUrl;
    private List<Empresa> empresas = new ArrayList<>();
    private Boolean verificado = false;
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    public static class Empresa {
        private String companyId;
        private String cargoEnEmpresa;
        private Boolean activo;
    }

    public static RecruiterProfile create(String userId) {
        RecruiterProfile profile = new RecruiterProfile();
        profile.setUserId(userId);
        profile.setVerificado(false);
        profile.setCreatedAt(Instant.now());
        profile.setUpdatedAt(Instant.now());
        return profile;
    }
}
