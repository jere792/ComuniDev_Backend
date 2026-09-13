package com.comunidev.comunidevbackend.users.application.dto;

import com.comunidev.comunidevbackend.developer_profile.domain.DeveloperProfile;
import com.comunidev.comunidevbackend.recruiter_profile.domain.RecruiterProfile;
import com.comunidev.comunidevbackend.users.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response")
public class UserResponse {

    @Schema(description = "User ID", example = "507f1f77bcf86cd799439011")
    private String id;

    @Schema(description = "User's full name", example = "Carlos Ruiz")
    private String nombre;

    @Schema(description = "Username", example = "carlosdev")
    private String nombreUsuario;

    @Schema(description = "Email", example = "carlos@correo.com")
    private String email;

    @Schema(description = "Phone number", example = "+51999888777")
    private String telefono;

    @Schema(description = "Profile picture URL")
    private String fotoPerfilUrl;

    @Schema(description = "Banner URL")
    private String bannerUrl;

    @Schema(description = "User bio")
    private String bio;

    @Schema(description = "User website/portfolio URL")
    private String sitioWeb;

    @Schema(description = "User location")
    private User.Ubicacion ubicacion;

    @Schema(description = "User roles")
    private Set<String> roles;

    @Schema(description = "Active role", example = "DEVELOPER")
    private String rolActivo;

    @Schema(description = "Account status", example = "ACTIVE")
    private String estadoCuenta;

    @Schema(description = "Email verified", example = "true")
    private Boolean emailVerificado;

    @Schema(description = "Followers count", example = "10")
    private Integer seguidoresCount;

    @Schema(description = "Following count", example = "25")
    private Integer siguiendoCount;

    @Schema(description = "Connections count", example = "15")
    private Integer conexionesCount;

    @Schema(description = "Creation date")
    private String createdAt;

    @Schema(description = "Developer profile (if user has DEVELOPER role)")
    private DeveloperProfile developerProfile;

    @Schema(description = "Recruiter profile (if user has RECRUITER role)")
    private RecruiterProfile recruiterProfile;
}
