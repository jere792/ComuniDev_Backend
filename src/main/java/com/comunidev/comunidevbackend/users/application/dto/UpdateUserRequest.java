package com.comunidev.comunidevbackend.users.application.dto;

import com.comunidev.comunidevbackend.users.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update user")
public class UpdateUserRequest {

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

    @Schema(description = "Active role", example = "DEVELOPER")
    private String rolActivo;
}
