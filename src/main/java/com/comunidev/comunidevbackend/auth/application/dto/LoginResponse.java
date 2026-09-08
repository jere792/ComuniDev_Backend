package com.comunidev.comunidevbackend.auth.application.dto;

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
@Schema(description = "Login response")
public class LoginResponse {

    @Schema(description = "User ID")
    private String id;

    @Schema(description = "User's full name")
    private String nombre;

    @Schema(description = "Username")
    private String nombreUsuario;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "User roles")
    private Set<String> roles;

    @Schema(description = "Active role")
    private String rolActivo;

    @Schema(description = "Account status")
    private String estadoCuenta;

    @Schema(description = "Access token")
    private String token;

    @Schema(description = "Whether user needs to select a role")
    private Boolean needsRoleSelection = false;
}
